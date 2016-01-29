package ch.squan.game.server

import akka.actor.ActorRef
import ch.squan.game.{ShipStateUpdate, OutgoingStateUpdate, StateUpdate}
import ch.squan.game.client.model.ship.Ship
import ch.squan.game.shared.model.GameState
import org.newdawn.slick.{Image, Graphics, BasicGame, GameContainer}

/**
  * Created by chris on 23/01/16.
  */
class GameServer(state:GameState,subscriber:ActorRef)
  extends BasicGame("server") {

  val timeStep = 1.0f / 60.0f
  val velocityIterations = 6
  val positionIterations = 2

  def init(gc: GameContainer): Unit = {
    val image = new Image("large.png")
  }

  def render(gc: GameContainer, g: Graphics):Unit = {
    state.objects.values foreach { x => x.draw(gc,g) }
  }

  def update(gc: GameContainer, delta: Int): Unit = {

    //Refresh
    val update = state.update.getAndSet(null)
    if(update!=null) { syncState(state, update) }

    //Do computation
    state.objects.values.foreach { o => o.update(gc, delta) }
    state.world.step(timeStep, velocityIterations, positionIterations)

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
          val ship = new Ship(state, o.physics.x, o.physics.y, o.physics.angle, o.ship.speed, o.ship.turning, o.ship.imgPath, o.id)
          ship.setPhysicsState(o.physics)
          state.objects += o.id -> ship
      }
    })
  }

}
