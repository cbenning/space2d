package ch.squan.game.model.ship

import ch.squan.game.model.command._
import org.jbox2d.dynamics.World
import org.newdawn.slick.command.{InputProvider, KeyControl}
import org.newdawn.slick.{GameContainer, Input}

/**
  * Created by chris on 22/01/16.
  */
class PlayerShip(gc: GameContainer, world:World,x:Float,y:Float,angle:Float)
  extends LargeFighter(world,x,y,angle) {

  val provider = new InputProvider(gc.getInput());
  provider.addListener(this)
  provider.bindCommand(new KeyControl(Input.KEY_W), CommandUp)
  provider.bindCommand(new KeyControl(Input.KEY_UP), CommandUp)
  provider.bindCommand(new KeyControl(Input.KEY_S), CommandDown)
  provider.bindCommand(new KeyControl(Input.KEY_DOWN), CommandDown)
  provider.bindCommand(new KeyControl(Input.KEY_A), CommandLeft)
  provider.bindCommand(new KeyControl(Input.KEY_LEFT), CommandLeft)
  provider.bindCommand(new KeyControl(Input.KEY_D), CommandRight)
  provider.bindCommand(new KeyControl(Input.KEY_RIGHT), CommandRight)
  provider.bindCommand(new KeyControl(Input.KEY_Q), CommandStrafeLeft)
  provider.bindCommand(new KeyControl(Input.KEY_RCONTROL), CommandStrafeLeft)
  provider.bindCommand(new KeyControl(Input.KEY_E), CommandStrafeRight)
  provider.bindCommand(new KeyControl(Input.KEY_NUMPAD0), CommandStrafeRight)
  provider.bindCommand(new KeyControl(Input.KEY_SPACE), CommandFire)

}
