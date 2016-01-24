package ch.squan.game

import ch.squan.game.model.ship.{Ship, PlayerShip}
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.World
import org.newdawn.slick._

/**
  * Created by chris on 16/01/16.
  */
class Game
  extends BasicGame("shit son") {

  var objects = Vector.empty[Ship]
  val gravity = new Vec2(0.0f,0.0f)
  val world = new World(gravity)

  val timeStep = 1.0f / 60.0f
  val velocityIterations = 6
  val positionIterations = 2

  var level:Level = null
  var background:Image = null
  var player:PlayerShip = null

  def init(gc: GameContainer): Unit = {
    background = new Image("starfield.png")
    level = new Level
    player = new PlayerShip(gc,world,0,0,0)
    Camera.follow(player)
    Camera.setSize(gc.getWidth,gc.getHeight)
  }

  def render(gc: GameContainer, g: Graphics) {
    level.draw(gc,g)
    player.draw(gc,g)
    objects foreach { x => x.draw(gc,g) }
  }

  def update(gc: GameContainer, delta: Int): Unit = {
    player.update(gc,delta)
    objects foreach { x => x.update(gc,delta)}
    world.step(timeStep, velocityIterations, positionIterations);
  }

}