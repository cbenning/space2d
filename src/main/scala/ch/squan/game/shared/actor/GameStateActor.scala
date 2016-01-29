//package ch.squan.game.shared.actor
//
//import akka.actor.{ActorRef, Props, Actor, ActorLogging}
//import ch.squan.game.{OutgoingStateUpdate, ShipStateUpdate, StateUpdate}
//import ch.squan.game.shared.model._
//
//object GameStateActor {
//  def props(state:GameState,gameServerActor: ActorRef): Props = Props(new GameStateActor(state,gameServerActor))
//}
//
///**
//  * Created by chris on 28/01/16.
//  */
//class GameStateActor(state:GameState, gameServerActor: ActorRef)
//  extends Actor
//  with ActorLogging {
//
//  val h = context.actorOf(HeartbeatActor.props(self),"heartbeat")
//
//  override def receive: Receive = {
//
//    /**
//      *
//      */
//    case su:StateUpdate =>
//      state.update.set(su)
//
//    /**
//      *
//      */
//    case CmdStateUpdate =>
//      val objects = state.refreshFlag.get
//      if(objects != null) {
//        gameServerActor ! new OutgoingStateUpdate(
//          new StateUpdate(objects.map(o => {
//            new ShipStateUpdate(o.id, o.getShipDetails, o.getPhysicsState)
//          })))
//      }
//  }
//}
