import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.World
import org.newdawn.slick._

/**
  * Created by chris on 16/01/16.
  */
class TestGame
  extends BasicGame("shit son") {

  var objects = Vector.empty[Ship]
  val gravity = new Vec2(0.0f,0.0f)
  val world = new World(gravity);

  val timeStep = 1.0f / 60.0f;
  val velocityIterations = 6;
  val positionIterations = 2;

  def init(gc: GameContainer): Unit = {
    objects = objects :+ new PlayerShip(gc,world)
  }

  def render(gc: GameContainer, g: Graphics) {
    objects foreach { x => x.draw(gc,g) }
  }

  def update(gc: GameContainer, delta: Int): Unit = {
    objects foreach { x => x.update(gc,delta)}
    world.step(timeStep, velocityIterations, positionIterations);
  }

}