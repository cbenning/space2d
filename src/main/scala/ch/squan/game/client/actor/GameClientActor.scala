package ch.squan.game.client.actor

import java.util.UUID

import akka.actor._
import akka.util.Timeout
import ch.squan.game.client.GameClient
import ch.squan.game.client.model.ship.{PlayerShip, Ship}
import ch.squan.game._
import ch.squan.game.shared.actor.HeartbeatActor
import ch.squan.game.shared.model.GameState
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

  val state = new GameState
//  val stateActor = context.actorOf(GameStateActor.props(state,self),"gamestate")
  val h = context.actorOf(HeartbeatActor.props(self,state),"heartbeat")
  val app = new AppGameContainer(new GameClient(state,self))
  app.setMaximumLogicUpdateInterval(8)
  app.setMinimumLogicUpdateInterval(5)

  val id = UUID.randomUUID.toString

  app.setDisplayMode(1024, 768, false)

  var servers = Map.empty[String,ActorRef]
  implicit val timeout = new Timeout(10 seconds)
  val a = Future { app.start() }

  override def preStart = {
    val selection = context.actorSelection("akka.tcp://gameserver@192.168.1.90:4200/user/gameserver")
//    val selection = context.actorSelection("akka.udp://gameserver@192.168.1.90:4201/user/gameserver")
    selection ! ClientConnect(id)
    log.debug("Sent connection message...")
  }

  override def receive: Receive = {

    case ClientConnected(id:String) =>
      servers += id -> sender
      log.warning("New server connected: {}",id)

    case su:StateUpdate => state.update.set(su)

    case OutgoingStateUpdate(su:StateUpdate) => servers.values.foreach( _ ! su)

    case e => log.warning("Got an unexpected message: {}")

  }

}
