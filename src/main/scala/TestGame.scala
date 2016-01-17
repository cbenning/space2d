import org.newdawn.slick.{Color, Graphics, GameContainer, BasicGame}
import org.newdawn.slick.geom.Circle

/**
  * Created by chris on 16/01/16.
  */
class TestGame extends BasicGame("shit son") {

  var objects = Vector.empty[Circle]
  var current = new Circle(0, 0, 0)
  var everPressed = false
  def init(gc: GameContainer) {}
  def render(gc: GameContainer, g: Graphics) {
    g.setColor(Color.red)
    objects foreach { x =>
      g.draw(x)
    }
    g.setColor(Color.yellow)
    g.draw(current)
  }

  def update(gc: GameContainer, delta: Int) {
    val input = gc.getInput

    val x = input.getMouseX()
    val y = input.getMouseY()

    if (input.isMouseButtonDown(0)) {
      everPressed = true
      if (current.getX() != 0 && current.getY() != 0) {
        val cx = current.getX()
        val cy = current.getY()
        val radius = scala.math.sqrt(scala.math.pow(x - cx, 2) + scala.math.pow(y - cy, 2))
        current.setRadius(radius.toFloat)
      } else {
        current.setX(x)
        current.setY(y)
        current.setRadius(0)
      }
    } else if(everPressed == true) {
      objects = objects :+ current
      current = new Circle(0, 0, 0)
      everPressed = false
    }
  }

}