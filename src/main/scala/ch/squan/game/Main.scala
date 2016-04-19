package ch.squan.game

import akka.actor.ActorSystem
import ch.squan.game.client.actor.GameClientActor
import ch.squan.game.server.actor.GameServerActor
import com.typesafe.config.ConfigFactory

/**
  * Created by chris on 23/01/16.
  */
object Main extends App {

  val config = ConfigFactory.load
  if(args.length>0 && args(0).equals("server")) {
    val system = ActorSystem.create("gameserver",config.getConfig("server").withFallback(config))
    val g = system.actorOf(GameServerActor.props,"gameserver")
  }
  else if(args.length>0 && args(0).equals("client1")) {
    val system = ActorSystem.create("gameclient1", config.getConfig("client1").withFallback(config))
    val g = system.actorOf(GameClientActor.props,"gameclient1")
  }
  else if(args.length>0 && args(0).equals("client2")) {
    val system = ActorSystem.create("gameclient2", config.getConfig("client2").withFallback(config))
    val g = system.actorOf(GameClientActor.props,"gameclient2")
  }
}
