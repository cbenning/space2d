package ch.squan.game.model.projectile

import ch.squan.game.model.ship.Ship
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.{BodyDef, BodyType, World}
import org.newdawn.slick.geom.Vector2f
import org.newdawn.slick.{GameContainer, Graphics, Image}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by chris on 22/01/16.
  */
class Laser(world:World,x:Float,y:Float,angle:Float,expireMillis:Int)
  extends Projectile {

  val img = new Image("laser-red.png")
  var _expired = false

  //
  //Box2D stuff
  //
  val bodyDef = new BodyDef
  bodyDef.`type` = BodyType.DYNAMIC
  bodyDef.position.set(x,y);   // the body's origin position.
  bodyDef.angle = angle      // the body's angle in radians.
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

//  val angle = body.getAngle
  val tmpVec = new Vector2f(angle)
  val rot = new Vec2(tmpVec.getX,tmpVec.getY)

  img.setRotation(angle+90)
  body.applyForce(rot.mul(2000.0f),body.getWorldCenter)



  val f = Future { Thread.sleep(expireMillis); _expired = true }

  override def isExpired = _expired

  override def update(gc: GameContainer, delta: Int) = { }

  override def draw(gc: GameContainer, g: Graphics):Unit = {
    img.draw(body.getPosition.x,body.getPosition.y)
  }

}
