import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.{FixtureDef, BodyType, World, BodyDef}
import org.newdawn.slick.geom.Vector2f
import org.newdawn.slick.{Graphics, GameContainer, Image}
import org.newdawn.slick.command.{Command, InputProviderListener}

/**
  * Created by chris on 22/01/16.
  */
class Laser(world:World,ship:Ship) {

  val img = new Image("laser-red.png")

  //
  //Box2D stuff
  //
  val bodyDef = new BodyDef
  bodyDef.`type` = BodyType.DYNAMIC
  bodyDef.position.set(ship.imgCenterX,ship.imgCenterY);   // the body's origin position.
  bodyDef.angle = ship.angle      // the body's angle in radians.
  bodyDef.linearDamping = 0.0f
  bodyDef.angularDamping = 0.0f
  bodyDef.gravityScale = 0.0f // Set the gravity scale to zero so this body will float
  bodyDef.allowSleep = false

  //fixture def
//  val fixtureDef = new FixtureDef
//  fixtureDef.shape = dynamicBox;
//  fixtureDef.density = 5.0f;
//  fixtureDef.friction = 0.0f;

  //Fire it up
  val body = world.createBody(bodyDef)
//  body.createFixture(fixtureDef)

  val angle = body.getAngle
  val tmpVec = new Vector2f(angle)
  val rot = new Vec2(tmpVec.getX,tmpVec.getY)

  img.setRotation(angle+90)
  body.applyForce(rot.mul(2000.0f),body.getWorldCenter)

  def update(gc: GameContainer, delta: Int) = { }

  def draw(gc: GameContainer, g: Graphics):Unit = {
    img.draw(body.getPosition.x,body.getPosition.y)
  }

}
