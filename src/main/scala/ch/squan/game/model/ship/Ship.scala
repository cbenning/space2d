package ch.squan.game.model.ship

import ch.squan.game._
import ch.squan.game.model.command._
import ch.squan.game.model.projectile.{Projectile, Laser}
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.{BodyDef, BodyType, FixtureDef, World}
import org.newdawn.slick.command.{Command, InputProviderListener}
import org.newdawn.slick.geom.Vector2f
import org.newdawn.slick.{GameContainer, Graphics, Image}

/**
  * Created by chris on 22/01/16.
  */
class Ship(world:World)
  extends InputProviderListener {

  val img = new Image("large.png")
  img.getWidth
  img.getHeight

  var projectiles = Vector.empty[Projectile]

  //
  //Box2D stuff
  //
  val bodyDef = new BodyDef
  bodyDef.`type` = BodyType.DYNAMIC
  bodyDef.position.set(50,50);   // the body's origin position.
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
//  dynamicBox.setRadius(1)
  dynamicBox.setAsBox(1.0f, 1.0f)

  //fixture def
  val fixtureDef = new FixtureDef
  fixtureDef.shape = dynamicBox
  fixtureDef.density = 5.0f
  fixtureDef.friction = 0.0f

  //Fire it up
  val body = world.createBody(bodyDef)
  body.createFixture(fixtureDef)

  //ch.squan.game.model.ship.Ship movements
  var up,down,left,right = false

  override def controlPressed(cmd:Command):Unit = cmd match {
    case CommandUp => up=true
    case CommandDown => down=true
    case CommandLeft => left=true
    case CommandRight => right=true
    case CommandFire => projectiles = projectiles :+ fireLaser
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
    //Draw ship
    img.draw(body.getWorldCenter.x,body.getWorldCenter.y)
    //Remove expired
    projectiles = projectiles.filter(p => !p.isExpired).map(p => {p.draw(gc,g); p})
  }

  /**
    *
    * @return
    */
  def fireLaser:Laser = new Laser(world,
        projectileSourcePosition.x,
        projectileSourcePosition.y,
        projectileSourceAngle,
        1000)

  /**
    *
    * @return
    */
  def projectileSourcePosition:Vec2 = {
    val pos = body.getPosition
    new Vec2(pos.x+(img.getWidth/2),pos.y)
  }

  /**
    *
    * @return
    */
  def projectileSourceAngle:Float = body.getAngle

}
