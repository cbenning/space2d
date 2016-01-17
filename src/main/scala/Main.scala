import org.newdawn.slick.AppGameContainer

object Main extends App {

  val app = new AppGameContainer(new TestGame)
  app.setDisplayMode(800, 600, false)
  app.start()

}

