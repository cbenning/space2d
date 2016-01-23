import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.{FixtureDef, BodyType, World, BodyDef}
import org.newdawn.slick.geom.Vector2f
import org.newdawn.slick.{Graphics, GameContainer, Image}
import org.newdawn.slick.command.{Command, InputProviderListener}

/**
  * Created by chris on 22/01/16.
  */
class Ship(world:World)
  extends InputProviderListener {

  val img = new Image("large.png")

  var projectiles = Vector.empty[Laser]

  //
  //Box2D stuff
  //
  val bodyDef = new BodyDef
  bodyDef.`type` = BodyType.DYNAMIC
  bodyDef.position.set(0,0);   // the body's origin position.
//  bodyDef.angle = b2_pi      // the body's angle in radians.
  bodyDef.linearDamping = 0.02f
  bodyDef.angularDamping = 0.4f
  bodyDef.gravityScale = 0.0f // Set the gravity scale to zero so this body will float
  bodyDef.allowSleep = false
  //bodyDef.awake = true;
  //bodyDef.fixedRotation = false;
  //bodyDef.userData = this;
  //bodyDef.active = true;

  //shape Def
  val dynamicBox = new PolygonShape
  dynamicBox.setAsBox(1, 1)

  //fixture def
  val fixtureDef = new FixtureDef
  fixtureDef.shape = dynamicBox;
  fixtureDef.density = 5.0f;
  fixtureDef.friction = 0.0f;

  //Fire it up
  val body = world.createBody(bodyDef)
  body.createFixture(fixtureDef)

  //Ship movements
  var up,down,left,right = false

  override def controlPressed(cmd:Command):Unit = cmd match {
    case CommandUp => up=true
    case CommandDown => down=true
    case CommandLeft => left=true
    case CommandRight => right=true
    case CommandFire => projectiles = projectiles :+ new Laser(world,this)
    case e => println("something weird")
  }

  override def controlReleased(cmd:Command):Unit = cmd match {
    case CommandUp => up=false
    case CommandDown => down=false
    case CommandLeft => left=false
    case CommandRight => right=false
    case e => println("something weird")
  }

  def update(gc: GameContainer, delta: Int) {

    val angle = body.getAngle
    val tmpVec = new Vector2f(angle).normalise
    val rot = new Vec2(tmpVec.getX,tmpVec.getY)

    //Forward/Reverse thrust
    if(up){ body.applyForce(rot.mul(50.0f),body.getWorldCenter) }
    if(down){ body.applyForce(rot.mul(-50.0f),body.getWorldCenter) }

    //Left/Right thrust
    if(left){ body.applyTorque(-100.0f) }
    if(right){ body.applyTorque(100.0f) }

    img.setRotation(angle)
  }

  def draw(gc: GameContainer, g: Graphics):Unit = {
    img.draw(body.getPosition.x,body.getPosition.y)
    projectiles.foreach(p => p.draw(gc,g))
  }

  def x = body.getPosition.x
  def y = body.getPosition.y
  def centerX = body.getWorldCenter.x
  def centerY = body.getWorldCenter.y
  def angle = body.getAngle

}
