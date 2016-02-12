package ch.squan.game.server.actor

import java.util.UUID

import akka.actor.{ActorRef, Props, ActorLogging, Actor}
import ch.squan.game.client.model.command.{CommandHelper, CommandUp}
import ch.squan.game.server.GameServer
import ch.squan.game._
import ch.squan.game.shared.actor.HeartbeatActor
import ch.squan.game.shared.model.{OutgoingShipCommand, ShipCommand, GameState}
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

  val state = new GameState
  val h = context.actorOf(HeartbeatActor.props(self,state),"heartbeat")
  val app = new AppGameContainer(new GameServer(state,self))

  app.setDisplayMode(100, 100, false)
  val a = Future { app.start() }

  val id = UUID.randomUUID.toString
  var clients = Map.empty[String,ActorRef]

  override def receive: Receive = {

    case ClientConnect(id:String) =>
      log.warning("New client connected: {}",id)
      clients += id -> sender
      sender ! ClientConnected(id)

    case su:StateUpdate => state.update.set(su)

    case OutgoingStateUpdate(su:StateUpdate) => clients.values.foreach( _ ! su)

    case sc:ShipCommand => state.objects.get(sc.id) match {
      case Some(s) =>
        clients.values.foreach( _ ! sc )
        log.warning("received remote input")
        log.warning("received remote input")
        if(sc.pressed) s.controlPressed(CommandHelper.nameToCommand(sc.cmd))
        else s.controlReleased(CommandHelper.nameToCommand(sc.cmd))
      case None => //
    }

    case OutgoingShipCommand(sc:ShipCommand) => clients.values.foreach( _ ! sc)

    case e => log.warning("Got an unexpected message: {}")
  }


}
