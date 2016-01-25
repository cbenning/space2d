package ch.squan.game.client.actor

import akka.actor._
import ch.squan.game.client.message.ClientCommandStateUpdate

import scala.concurrent.duration._

object ClientStateHeartbeatActor {
  def props(gameServer:ActorRef): Props = Props(new ClientStateHeartbeatActor(gameServer))
}

/**
  * Created by chris on 23/01/16.
  */
class ClientStateHeartbeatActor(gameServer:ActorRef)
  extends Actor
  with ActorLogging {

  context.setReceiveTimeout(30 milliseconds)
  override def receive: Receive = {
    case ReceiveTimeout =>
      gameServer ! ClientCommandStateUpdate
      context.setReceiveTimeout(30 milliseconds)
  }

}
