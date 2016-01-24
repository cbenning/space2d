package ch.squan.game.model.background

import org.newdawn.slick.{Graphics, GameContainer}

/**
  * Created by chris on 23/01/16.
  */
class Parallax(layers:Vector[ParallaxLayer]) {

  def update(gc:GameContainer, delta:Int):Unit = {
    layers.foreach(l=>l.update(gc,delta))
  }

  def render(gc:GameContainer, g:Graphics):Unit = {
    layers.foreach(l=>l.render(gc,g))
  }

}
