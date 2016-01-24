package ch.squan.game.model.ship

import ch.squan.game.Camera
import ch.squan.game.model.command._
import org.jbox2d.dynamics.World
import org.newdawn.slick.command.{InputProvider, KeyControl}
import org.newdawn.slick.{Graphics, GameContainer, Input}

/**
  * Created by chris on 22/01/16.
  */
class PlayerShip(gc: GameContainer, world:World,x:Float,y:Float,angle:Float)
  extends LargeFighter(world,x,y,angle) {

  val provider = new InputProvider(gc.getInput());
  provider.addListener(this)
  provider.bindCommand(new KeyControl(Input.KEY_LEFT), CommandLeft)
  provider.bindCommand(new KeyControl(Input.KEY_UP), CommandUp)
  provider.bindCommand(new KeyControl(Input.KEY_RIGHT), CommandRight)
  provider.bindCommand(new KeyControl(Input.KEY_DOWN), CommandDown)
  provider.bindCommand(new KeyControl(Input.KEY_SPACE), CommandFire)


  /**
    *
    * @param gc
    * @param g
    */
  override def draw(gc: GameContainer, g: Graphics):Unit = {
    //Draw ship
    img.draw(Camera.x,Camera.y)
    //Remove expired
    projectiles = projectiles.filter(p => !p.isExpired).map(p => {p.draw(gc,g); p})
  }

}
