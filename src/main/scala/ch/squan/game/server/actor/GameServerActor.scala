package ch.squan.game.server.actor

import java.util.UUID

import akka.actor.{ActorRef, Props, ActorLogging, Actor}
import ch.squan.game.server.GameServer
import ch.squan.game.server.message.ServerCommandStateUpdate
import ch.squan.game._
import ch.squan.game.client.model.ship.Ship
import org.newdawn.slick.AppGameContainer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object GameServerActor {
  def props: Props = Props(new GameServerActor)
}

/**
  * Created by chris on 23/01/16.
  */
class GameServerActor
  extends Actor
  with ActorLogging {

  val state = new GameState(self)
  val app = new AppGameContainer(new GameServer(state))
  app.setDisplayMode(100, 100, false)
  val a = Future { app.start() }

  val id = UUID.randomUUID.toString
  var clients = Map.empty[String,ActorRef]

  override def receive: Receive = {

    case ClientConnect(id:String) =>
      log.warning("New client connected: {}",id)
      clients += id -> sender
      sender ! ClientConnected(id)

//    case s:ShipSpawn =>
//      state.objects += s.id -> new Ship(state,s.x,s.y,s.angle,s.speed,s.turning,s.imgPath)
//      log.debug("Spawn new ship: {}",s.id)

//    case s:ShipStateUpdate =>
//      state.objects.get(s.id) match {
//        case Some(o) => o.setPhysicsState(s.physics)
//        case None => log.warning("Got state update for non-existent state")
//      }

//    case StateUpdate(objects) =>
//      objects.foreach(o => { state.objects.get(o.id) match {
//          case Some(s) => s.setPhysicsState(o.physics)
//          case e => log.warning("Got state update for non-existent object")
//        }
//      })

    case StateUpdate(objects) =>
      objects.foreach(o => { state.objects.get(o.id) match {
          case Some(s) =>
            s.setPhysicsState(o.physics)
          case e =>
            log.warning("Got state update for non-existent object, creating it...")
            val ship = new Ship(state,o.physics.x,o.physics.y,o.physics.angle,o.ship.speed,o.ship.turning,o.ship.imgPath,o.id)
            ship.setPhysicsState(o.physics)
            state.objects += o.id -> ship
        }
      })

    case ServerCommandStateUpdate =>
      val states = new StateUpdate(state.objects.values.map( o => {
        new ShipStateUpdate(o.id,o.getShipDetails,o.getPhysicsState)
       }))
//      if(clients.size>0){log.warning("Sending state update")}
      clients.values.foreach( _ ! states )

    case e => log.warning("Got an unexpected message: {}")
  }


}
