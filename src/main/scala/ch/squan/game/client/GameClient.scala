package ch.squan.game.client

import ch.squan.game.client.model.ship.{PlayerShip}
import ch.squan.game._
import org.newdawn.slick._

/**
  * Created by chris on 16/01/16.
  */
class GameClient(state:GameState)
  extends BasicGame("client") {

  val timeStep = 1.0f / 60.0f
  val velocityIterations = 6
  val positionIterations = 2

  def init(gc: GameContainer): Unit = {
    gc.setAlwaysRender(true) //Do not stop while not in focus
    state.level = new Level(GameProperties.levelWidth,GameProperties.levelHeight)
    state.player = new PlayerShip(gc,state,0,0,0)
    Camera.follow(state.player)
    Camera.setSize(gc.getWidth,gc.getHeight)
  }

  def render(gc: GameContainer, g: Graphics) {
    Camera.draw(gc,g) //Must come first
    state.level.draw(gc,g)
    state.player.draw(gc,g)
    state.objects.values foreach { x => x.draw(gc,g) }
  }

  def update(gc: GameContainer, delta: Int): Unit = {
    state.player.update(gc,delta)
    state.objects.values foreach { x => x.update(gc,delta)}
    state.world.step(timeStep, velocityIterations, positionIterations)
  }

}