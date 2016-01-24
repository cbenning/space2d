package ch.squan.game

import org.newdawn.slick.{Graphics, GameContainer, Image}

/**
  * Created by chris on 23/01/16.
  */
class Level {
  val background = new Image("starfield.png")

  /**
    *
    * @param gc
    * @param g
    */
  def draw(gc: GameContainer, g: Graphics): Unit = {
    g.translate(-Camera.x+(gc.getWidth/2),-Camera.y+(gc.getHeight/2))
    background.draw(0,0)
  }

}
