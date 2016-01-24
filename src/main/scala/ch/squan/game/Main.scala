package ch.squan.game

import org.newdawn.slick.AppGameContainer

/**
  * Created by chris on 23/01/16.
  */
object Main extends App {
  val app = new AppGameContainer(new Game)
  app.setDisplayMode(1024, 768, false)
  app.start()
}
