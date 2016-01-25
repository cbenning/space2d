package ch.squan.game.client.model.background

import org.newdawn.slick.{Image, Graphics, GameContainer}

/**
  * Created by chris on 23/01/16.
  */
class ParallaxLayer(imgPath:String) {

  val img = new Image(imgPath)

  def update(gc:GameContainer, delta:Int):Unit = {
    //    layers.foreach(l=>l.update(gc,delta))
  }

  def render(gc:GameContainer, g:Graphics):Unit = {
//    layers.foreach(l=>l.render(gc,g))
//
//    g.setCl
//
//    g.setClip(0, 0, gc.getWidth(), gc.getHeight());
//
//                        for (int x = getStartX(); x < gc.getWidth() + settings.getWidth(); x += settings.getWidth()) {
//                                for (int y = getStartY(); y < gc.getHeight() + settings.getHeight(); y += settings.getHeight()) {
//                                        tile.draw(x + getXClip(), y + getYClip(), settings.getWidth(), settings.getHeight(), settings.getFilter());
//                                }
//                        }
//
//                        g.clearClip();
  }
}
