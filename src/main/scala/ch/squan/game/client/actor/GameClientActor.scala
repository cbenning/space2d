package ch.squan.game.client.actor

import java.util.UUID

import akka.actor._
import akka.util.Timeout
import ch.squan.game.client.GameClient
import ch.squan.game.client.message.ClientCommandStateUpdate
import ch.squan.game.client.model.ship.{PlayerShip, Ship}
import ch.squan.game._
import org.newdawn.slick.AppGameContainer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration

object GameClientActor {
  def props: Props = Props(new GameClientActor)
}

/**
  * Created by chris on 23/01/16.
  */
class GameClientActor
  extends Actor
  with ActorLogging {

  val id = UUID.randomUUID.toString

  val state = new GameState(self)
  val app = new AppGameContainer(new GameClient(state))
  app.setDisplayMode(1024, 768, false)

  val a = Future { app.start() }

  var servers = Map.empty[String,ActorRef]
  implicit val timeout = new Timeout(10 seconds)

  override def preStart = {
    val selection = context.actorSelection("akka.tcp://gameserver@127.0.0.1:4200/user/gameserver")
    selection ! ClientConnect(id)
//    val f = selection.resolveOne
//    val r = Await.result(f,Duration.Inf)
//    log.debug("Connected remote actor")
//    r ! ClientConnect(id)
    log.debug("Sent connection message...")
  }

  override def receive: Receive = {

    case ClientConnected(id:String) =>
      servers += id -> sender
      log.debug("New server connected: {}",id)

//    case s:ShipSpawn =>
//      state.objects = state.objects + (s.id -> new Ship(state,s.x,s.y,s.angle,s.speed,s.turning,s.imgPath))
//      log.debug("Spawn new ship: {}",s.id)

    case StateUpdate(objects) =>
      if(state.player != null ) {
        objects.filter(_.id != state.player.id ).foreach(o => { //Ignore player ship
          log.warning("Update for {}",o.id)
          state.objects.get(o.id) match {
            case Some(s) =>
                  s.setPhysicsState(o.physics)
            case e =>
              log.warning("Got state update for non-existent object, creating it...")
              val ship = new Ship(state, o.physics.x, o.physics.y, o.physics.angle, o.ship.speed, o.ship.turning, o.ship.imgPath, o.id)
              ship.setPhysicsState(o.physics)
              state.objects += o.id -> ship
          }
        })
      }

    case ClientCommandStateUpdate =>
      if(state.player != null) {
//        log.warning("Sending state update")
        val states = new StateUpdate(
          List(
            new ShipStateUpdate(state.player.id,state.player.getShipDetails,state.player.getPhysicsState)))
        servers.values.foreach(_ ! states)
      }

    case e => log.warning("Got an unexpected message: {}")
  }

}
