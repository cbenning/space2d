package ch.squan.game.client

import akka.actor.ActorRef
import ch.squan.game.client.model.ship.{Ship, PlayerShip}
import ch.squan.game._
import ch.squan.game.shared.model.GameState
import org.newdawn.slick._
import akka.util.Timeout
import scala.concurrent.duration._


/**
  * Created by chris on 16/01/16.
  */
class GameClient(state:GameState, subscriber: ActorRef)
  extends BasicGame("client") {

  val timeStep = 1.0f / 60.0f
  val velocityIterations = 6
  val positionIterations = 2

  implicit val timeout = Timeout(5 seconds)

  def init(gc: GameContainer): Unit = {
    //gc.setTargetFrameRate(60)
    //gc.setVSync(true)
    gc.setAlwaysRender(true) //Do not stop while not in focus
    state.level = new Level(GameProperties.levelWidth, GameProperties.levelHeight)
    state.player = new PlayerShip(gc, state, 0, 0, 0)
    state.camera.follow(state.player)
    state.camera.setSize(gc.getWidth, gc.getHeight)
  }

  def render(gc: GameContainer, g: Graphics) {
    state.camera.draw(gc, g) //Must come first
    state.level.draw(gc, g)
    state.player.draw(gc, g)
    state.objects.values foreach { x => x.draw(gc, g) }
  }

  def update(gc: GameContainer, delta: Int): Unit = {
    //Refresh
    val update = state.update.getAndSet(null)
    if(update!=null) { syncState(state, update) }

    //Do computation
    state.player.update(gc, delta)
    state.objects.values foreach { x => x.update(gc, delta) }
    state.world.step(timeStep, velocityIterations, positionIterations)

    //Send update
    if(state.refreshFlag){ sendState(state); state.refreshFlag = false }
  }


  def saveState(state: GameState):Iterable[Ship] = { state.objects.values ++ Seq(state.player) }

  def sendState(state: GameState) = {
    val objects = saveState(state)
    if(objects != null) {
      subscriber ! new OutgoingStateUpdate(
        new StateUpdate(objects.map(o => {
          new ShipStateUpdate(o.id, o.getShipDetails, o.getPhysicsState)
        })))
    }
  }

  def syncState(state: GameState, stateUpdate: StateUpdate) = {
    if (state.player != null) {
      //only continue if player exists
      stateUpdate.objects.filter(_.id != state.player.id).foreach(o => {
        //filter player from results
        state.objects.get(o.id) match {
          case Some(s) =>
            s.setPhysicsState(o.physics)
          case e =>
            println("Got state update for non-existent object, creating it...")
            val ship = new Ship(state, o.physics.x, o.physics.y, o.physics.angle, o.ship.speed, o.ship.turning, o.ship.imgPath, o.id)
            ship.setPhysicsState(o.physics)
            state.objects += o.id -> ship
        }
      })
    }
  }

}