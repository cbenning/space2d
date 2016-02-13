package ch.squan.game.server

import akka.actor.ActorRef
import ch.squan.game._
import ch.squan.game.client.model.ship.Ship
import ch.squan.game.shared.model.GameState
import org.newdawn.slick.{Image, Graphics, BasicGame, GameContainer}

/**
  * Created by chris on 23/01/16.
  */
class GameServer(state:GameState,subscriber:ActorRef)
  extends BasicGame("server") {

  val velocityIterations = 6
  val positionIterations = 2

  val UPDATE_INTERVAL:Float = 1.0f/60.0f
  val MAX_CYCLES_PER_FRAME:Float = 5
  var timeAccumulator:Float = 0

  val PHYSICS_SPEED_MODIFIER = 1.0f

  def init(gc: GameContainer): Unit = {
//    gc.setVSync(true)
    gc.setTargetFrameRate(60)
    gc.setAlwaysRender(true) //Do not stop while not in focus
    state.level = new Level(GameProperties.levelWidth, GameProperties.levelHeight)
    state.camera.setSize(gc.getWidth, gc.getHeight)
    new Image("large.png")
    new Image("laser-red.png")
  }

  def render(gc: GameContainer, g: Graphics):Unit = {
    state.camera.draw(gc, g) //Must come first
    state.level.draw(gc, g)
    state.objects.values foreach { x => x.draw(gc, g) }
  }

  def update(gc: GameContainer, delta: Int): Unit = {

    //Refresh
    val update = state.update.getAndSet(null)
    if(update!=null) { syncState(state, update) }

    //Do computation
    state.objects.values.foreach { o => o.update(gc, delta) }

    timeAccumulator += delta
    if (timeAccumulator > (MAX_CYCLES_PER_FRAME * UPDATE_INTERVAL)) {
        timeAccumulator = UPDATE_INTERVAL
    }

    while (timeAccumulator >= UPDATE_INTERVAL) {
        timeAccumulator -= UPDATE_INTERVAL
        state.world.step(UPDATE_INTERVAL*PHYSICS_SPEED_MODIFIER, velocityIterations, positionIterations);
    }

//    val d = delta.toFloat/1000 * 100
//    state.world.step(d,velocityIterations, positionIterations)

    //Send update
    if(state.refreshFlag){ sendState(state); state.refreshFlag = false }
  }


  def saveState(state: GameState):Iterable[Ship] = { state.objects.values }

  def sendState(state: GameState) = {
    val objects = saveState(state)
    if(objects != null) {
      subscriber ! new OutgoingStateUpdate(
        new StateUpdate(objects.map(o => {
          new ShipStateUpdate(o.id, o.getShipDetails, o.getPhysicsState)
        })))
    }
  }

  def syncState(state: GameState, stateUpdate:StateUpdate) = {
    stateUpdate.objects.foreach(o => {
      state.objects.get(o.id) match {
        case Some(s) =>
          s.setPhysicsState(o.physics)
        case e =>
          println("Got state update for non-existent object, creating it...")
          val ship = new Ship(state,
              o.physics.x,
              o.physics.y,
              o.physics.angle,
              o.ship.mainEngineThrust,
              o.ship.rotationalEngineThrust,
              o.ship.strafeEngineThrust,
              o.ship.shipForeLength,
              o.ship.shipAftLength,
              o.ship.imgPath,
              o.id)

          ship.setPhysicsState(o.physics)
          state.objects += o.id -> ship
      }
    })
  }

}
