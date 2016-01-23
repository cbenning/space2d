import org.jbox2d.dynamics.World
import org.newdawn.slick.{GameContainer, Input}
import org.newdawn.slick.command.{KeyControl, InputProvider}

/**
  * Created by chris on 22/01/16.
  */
class PlayerShip(gc: GameContainer, world:World)
  extends Ship(world) {

  val provider = new InputProvider(gc.getInput());
  provider.addListener(this)
  provider.bindCommand(new KeyControl(Input.KEY_LEFT), CommandLeft)
  provider.bindCommand(new KeyControl(Input.KEY_UP), CommandUp)
  provider.bindCommand(new KeyControl(Input.KEY_RIGHT), CommandRight)
  provider.bindCommand(new KeyControl(Input.KEY_DOWN), CommandDown)

}
