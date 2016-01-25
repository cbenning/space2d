package ch.squan.game.client.model.ship

import akka.actor.ActorRef
import ch.squan.game.GameState
import ch.squan.game.client.model.command._
import org.jbox2d.dynamics.World
import org.newdawn.slick.command.{InputProvider, KeyControl}
import org.newdawn.slick.{GameContainer, Input}

/**
  * Created by chris on 22/01/16.
  */
class PlayerShip(gc: GameContainer, state:GameState,x:Float,y:Float,angle:Float)
  extends LargeFighter(state,x,y,angle) {

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
