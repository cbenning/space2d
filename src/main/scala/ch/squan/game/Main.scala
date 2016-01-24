package ch.squan.game

import org.newdawn.slick.AppGameContainer
import org.newdawn.slick.tests.xml.Entity
import org.newdawn.slick.tiled.TiledMap

/**
  * Created by chris on 23/01/16.
  */
object Main extends App {

  val app = new AppGameContainer(new Game)
  app.setDisplayMode(1024, 768, false)

//  Parallax
//  val farSetting = new ParallaxSettings(manager.getResource("SPACE_FAR", Image.class).get(), 100);
//  mapper = new ParallaxMapper();
//
//                  camera = new Entity();
//                camera.setBounds(0, 0, 0, gc.getWidth(), gc.getHeight(), 0);
//                mapper.attachTo(camera);

  app.start()
}
