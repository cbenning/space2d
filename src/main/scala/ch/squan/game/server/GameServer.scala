package ch.squan.game.server

import akka.actor.{ActorRef, ActorSystem}
import ch.squan.game.{Camera, GameState, GameProperties, Level}
import ch.squan.game.client.model.ship.Ship
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.World
import org.newdawn.slick.{Image, Graphics, BasicGame, GameContainer}

/**
  * Created by chris on 23/01/16.
  */
class GameServer(state:GameState)
  extends BasicGame("server") {

  val timeStep = 1.0f / 60.0f
  val velocityIterations = 6
  val positionIterations = 2

  def init(gc: GameContainer): Unit = {
//    state.level = new Level(GameProperties.levelWidth,GameProperties.levelHeight)
    val image = new Image("large.png")
  }


  def render(gc: GameContainer, g: Graphics) {
//    Camera.draw(gc,g) //Must come first
//    state.level.draw(gc,g)
//    state.player.draw(gc,g)
    state.objects.values foreach { x => x.draw(gc,g) }
  }

  def update(gc: GameContainer, delta: Int): Unit = {
    state.objects.values.foreach{ o => o.update(gc,delta) }
    state.world.step(timeStep, velocityIterations, positionIterations)
  }
}
