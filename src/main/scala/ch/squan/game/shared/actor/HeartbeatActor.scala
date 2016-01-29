package ch.squan.game.shared.actor

import akka.actor._
import ch.squan.game.shared.model.{GameState, CmdStateUpdate}

import scala.concurrent.duration._

object HeartbeatActor {
  def props(gameServer:ActorRef,state:GameState): Props = Props(new HeartbeatActor(gameServer,state))
}

/**
  * Created by chris on 23/01/16.
  */
class HeartbeatActor(gameServer:ActorRef,state:GameState)
  extends Actor
  with ActorLogging {

  context.setReceiveTimeout(30 milliseconds)
  override def receive: Receive = {
    case ReceiveTimeout =>
      state.refreshFlag = true
//      gameServer ! CmdStateUpdate
      context.setReceiveTimeout(30 milliseconds)
  }

}
