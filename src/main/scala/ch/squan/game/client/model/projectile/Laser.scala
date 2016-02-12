package ch.squan.game.client.model.projectile

import ch.squan.game.shared.Util
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.{FixtureDef, BodyDef, BodyType, World}
import org.newdawn.slick.geom.Vector2f
import org.newdawn.slick.{GameContainer, Graphics, Image}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by chris on 22/01/16.
  */
class Laser(world:World,x:Float,y:Float,angle:Float)
  extends Projectile
  with Util {

  val speed = 0.2f
  val expireMillis = 1200
  val imgScale = 0.5f
  val img = new Image("laser-red.png").getScaledCopy(imgScale)
  var _expired = false

  //
  //Box2D stuff
  //
  val bodyDef = new BodyDef
  bodyDef.`type` = BodyType.DYNAMIC
  bodyDef.position.set(x,y)
  bodyDef.angle = angle
  bodyDef.linearDamping = 0.0f
  bodyDef.angularDamping = 0.0f
  bodyDef.gravityScale = 0.0f
  bodyDef.allowSleep = false

  //polygon
  val dynamicBox = new PolygonShape
  dynamicBox.setAsBox(1, 1)

  //fixture def
  val fixtureDef = new FixtureDef
  fixtureDef.shape = dynamicBox
  fixtureDef.density = 0.0f
  fixtureDef.friction = 0.0f

  //Fire it up
  val body = world.createBody(bodyDef)
//  body.createFixture(fixtureDef)

  // val angle = body.getAngle
  val tmpVec = new Vector2f(angle)
  val rot = new Vec2(tmpVec.getX,tmpVec.getY)

  img.setRotation(angle+90)
  body.applyForce(rot.mul(speed),body.getWorldCenter)

  println(centerX,centerY)

  val f = Future { Thread.sleep(expireMillis); _expired = true }

  override def isExpired = _expired

  override def update(gc: GameContainer, delta: Int) = { }

  override def draw(gc: GameContainer, g: Graphics):Unit = {
    img.draw(centerX-(img.getWidth/2),centerY-(img.getHeight/4))
  }

  /**
    *
    * @return
    */
  def centerX:Float = (body.getWorldCenter.x/PHYSICS_SCALE) - (img.getWidth/2)

  /**
    *
    * @return
    */
  def centerY:Float = (body.getWorldCenter.y/PHYSICS_SCALE) - (img.getHeight/4)

}
