package ch.squan.game.server.actor

import akka.actor._
import ch.squan.game.server.message.ServerCommandStateUpdate
import scala.concurrent.duration._

object ServerStateHeartbeatActor {
  def props(gameServer:ActorRef): Props = Props(new ServerStateHeartbeatActor(gameServer))
}

/**
  * Created by chris on 23/01/16.
  */
class ServerStateHeartbeatActor(gameServer:ActorRef)
  extends Actor
  with ActorLogging {

  context.setReceiveTimeout(30 milliseconds)
  override def receive: Receive = {
    case ReceiveTimeout =>
      gameServer ! ServerCommandStateUpdate
      context.setReceiveTimeout(30 milliseconds)
  }

}
