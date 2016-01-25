package ch.squan.game

import org.newdawn.slick.{Graphics, GameContainer, Image}

/**
  * Created by chris on 23/01/16.
  */
class Level(w:Int,h:Int) {
  val background = new Image(GameProperties.backgroundImage)
  val tiles = Vector.tabulate(w/background.getWidth,h/background.getHeight){ (i,j) => (i,j)}

  /**
    *
    * @param gc
    * @param g
    */
  def draw(gc: GameContainer, g: Graphics): Unit = {
    for (
      i <- 0 to w/background.getWidth;
      j <- 0 to h/background.getWidth
    ) yield background.draw(i*background.getWidth, j*background.getHeight)
  }

}
