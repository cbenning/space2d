package ch.squan.game.client.model.ship

import java.util.UUID

import akka.actor.ActorRef
import ch.squan.game._
import ch.squan.game.client.model.command._
import ch.squan.game.client.model.projectile.{Projectile, Laser}
import ch.squan.game.shared.Util
import ch.squan.game.shared.model.{ShipCommand, OutgoingShipCommand, GameState}
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.{BodyDef, BodyType, FixtureDef, World}
import org.newdawn.slick.command.{BasicCommand, Command, InputProviderListener}
import org.newdawn.slick.geom.Vector2f
import org.newdawn.slick.{GameContainer, Graphics, Image}

/**
  * Created by chris on 22/01/16.
  */
class Ship(state:GameState,
           x:Float,
           y:Float,
           angle:Float,
           mainEngineThrust:Float,
           rotationalEngineThrust:Float,
           strafeEngineThrust:Float,
           shipForeLength:Float,
           shipAftLength:Float,
           imgPath:String,
           _id:String=UUID.randomUUID.toString)
  extends InputProviderListener
  with Util {

  val img = new Image(imgPath)
  var projectiles = Vector.empty[Projectile]

  def id = _id

  //
  //Box2D stuff
  //
  val bodyDef = new BodyDef
  bodyDef.`type` = BodyType.DYNAMIC
  bodyDef.position.set(x, y)
  bodyDef.angle = angle
  bodyDef.linearDamping = 0.2f
  bodyDef.angularDamping = 10.0f
  bodyDef.gravityScale = 0.0f // Set the gravity scale to zero so this body will float
  bodyDef.allowSleep = false

  //shape Def
  val dynamicBox = new PolygonShape
  dynamicBox.setAsBox(0.01f, 0.01f)

  //fixture def
  val fixtureDef = new FixtureDef
  fixtureDef.shape = dynamicBox
  fixtureDef.density = 1.0f
  fixtureDef.friction = 0.0f

  //Fire it up
  val body = state.world.createBody(bodyDef)
  body.setFixedRotation(false)
  body.createFixture(fixtureDef)

  //ch.squan.game.client.model.ship.Ship movements
  var up, down, left, right, strafel, strafer = false

  var _subscriber:ActorRef = null
  def subscriber = _subscriber
  def subscriber_=(newSubscriber:ActorRef) = _subscriber=newSubscriber

  /**
    *
    * @return
    */
  def getPhysicsState: PhysicsState = new PhysicsState(
    body.getWorldCenter.x,
    body.getWorldCenter.y,
    body.getAngle,
    body.getLinearVelocity,
    body.getAngularVelocity)

  /**
    *
    * @param s
    */
  def setPhysicsState(s: PhysicsState): Unit = {
    body.setTransform(
      new Vec2(s.x, s.y),
      s.angle)
    body.setAngularVelocity(s.angularVelocity)
    body.setLinearVelocity(s.linearVelocity)
  }

  /**
    *
    * @return
    */
  def getShipDetails: ShipState = new ShipState(mainEngineThrust, rotationalEngineThrust, strafeEngineThrust, shipForeLength, shipAftLength, imgPath)

  /**
    *
    * @param gameState
    * @return
    */
  def cloneFrom(gameState: GameState):Ship = {
    new Ship(gameState,x,y,angle,mainEngineThrust,rotationalEngineThrust,strafeEngineThrust,shipForeLength,shipAftLength,imgPath,_id)
  }

  /**
    *
    * @param gc
    * @param delta
    */
  def update(gc: GameContainer, delta: Int) {

    val angle = body.getAngle
    val tmpVec = new Vector2f(angle).normalise
    val rot = new Vec2(tmpVec.getX, tmpVec.getY)

    //Forward/Reverse thrust
    if (up) {
      body.applyForce(rot.mul(mainEngineThrust), body.getWorldCenter)
    }
    if (down) {
      body.applyForce(rot.mul(-mainEngineThrust), body.getWorldCenter)
    }

    //Strafe Left/Right thrust
    if (strafel) {
      val langle = angle - 90
      val tmpLVec = new Vector2f(langle).normalise
      val lrot = new Vec2(tmpLVec.getX, tmpLVec.getY)
      body.applyForce(lrot.mul(strafeEngineThrust), body.getWorldCenter)
    }
    if (strafer) {
      val rangle = angle + 90
      val tmpRVec = new Vector2f(rangle).normalise
      val rrot = new Vec2(tmpRVec.getX, tmpRVec.getY)
      body.applyForce(rrot.mul(strafeEngineThrust), body.getWorldCenter)
    }

    if(left) { //Left thrust
      val tmpFront = new Vector2f(angle).normalise
      val front = new Vec2(tmpFront.getX, tmpFront.getY)

      //Front end thruster - Left
      val tmpLeft = tmpFront.getPerpendicular
      val left = new Vec2(tmpLeft.getX, tmpLeft.getY)
      body.applyForce(left, body.getWorldCenter.add(front.mul(-shipForeLength)))

      //Rear end thruster - Right
      val right = left.negate
      body.applyForce(right, body.getWorldCenter.add(front.mul(shipAftLength)))
    }

    if(right) { //Right thrust
      val tmpFront = new Vector2f(angle).normalise
      val front = new Vec2(tmpFront.getX, tmpFront.getY)

      //Rear end thruster - Left
      val tmpLeft = tmpFront.getPerpendicular
      val left = new Vec2(tmpLeft.getX, tmpLeft.getY)
      body.applyForce(left, body.getWorldCenter.add(front.mul(shipAftLength)))

      //Front end thruster - Right
      val right = left.negate
      body.applyForce(right, body.getWorldCenter.add(front.mul(-shipForeLength)))
    }

    //      val tmpFront2 = new Vector2f(tmpFront.x+1,tmpFront.y+1)

    //      val tmpRear = new Vector2f(angle).normalise.sub(100)
    //      val tmpLeft = tmpFront.getPerpendicular.negate
    //      val tmpRight = tmpRear.getPerpendicular
    //      val right = new Vec2(tmpRight.getX,tmpRight.getY)
    //      val left = new Vec2(tmpLeft.getX,tmpLeft.getY)
    //      body.applyForce(right.mul(speed), body.getWorldCenter)
    //      body.applyForce(left.mul(speed), body.getWorldCenter)

    //      val langle = angle - 90
    //      val rangle = angle + 90

    //      val tmpLVec = new Vector2f(langle).normalise.add(1)
    //      val lrot = new Vec2(tmpLVec.getX, tmpLVec.getY)
    //      val tmpRVec = new Vector2f(langle).normalise.sub(1)
    //      val rrot = new Vec2(tmpLVec.getX, tmpLVec.getY)
    //      body.applyForce(lrot.mul(speed), body.getWorldCenter)
    //      body.applyForce(rrot.mul(speed), body.getWorldCenter)
//  }

  //    if(right) {
  //      val langle = angle - 90
  //      val rangle = angle + 90
  //      val tmpLVec = new Vector2f(langle).normalise.sub(1)
  //      val lrot = new Vec2(tmpLVec.getX, tmpLVec.getY)
  //      val tmpRVec = new Vector2f(langle).normalise.add(1)
  //      val rrot = new Vec2(tmpLVec.getX, tmpLVec.getY)
  //      body.applyForce(lrot.mul(speed), body.getWorldCenter)
  //      body.applyForce(rrot.mul(speed), body.getWorldCenter)
  //    }
  //    if(left){ body.applyForce(rotmul(speed), body.getWorldCenter)

//    if(left){ body.applyTorque(-turning*100) }
//    if(right){ body.applyTorque(turning*100) }

    img.setRotation(angle)
  }

  /**
    *
    * @param gc
    * @param g
    */
  def draw(gc: GameContainer, g: Graphics):Unit = {
    //Draw ship
    img.draw(centerX-(img.getWidth/2),centerY-(img.getHeight/4))
    //Remove expired
    projectiles = projectiles.filter(p => !p.isExpired).map(p => {p.draw(gc,g); p})
  }


  def physX:Float = body.getWorldCenter.x - ((img.getWidth/2)*PHYSICS_SCALE)

  def physY:Float = body.getWorldCenter.y - ((img.getHeight/4)*PHYSICS_SCALE)

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

  /**
    *
    * @return
    */
  def fireLaser:Laser = {println("fire"); new Laser(state.world, physX, physY, imgAngle)}

  /**
    *
    * @return
    */
  def imgAngle = body.getAngle

  override def controlPressed(cmd:Command):Unit = {
    cmd match {
      case bc:BasicCommand =>
        if(subscriber!=null){ subscriber ! new OutgoingShipCommand(new ShipCommand(id,true,bc.getName)) }  //Send to remote actor
        bc match {
          case CommandUp => up=true
          case CommandDown => down=true
          case CommandLeft => left=true
          case CommandRight => right=true
          case CommandStrafeLeft => strafel=true
          case CommandStrafeRight => strafer=true
          case CommandFire => projectiles = projectiles :+ fireLaser
          case e => println("something weird")
        }
    }

  }

  override def controlReleased(cmd:Command):Unit = {
    cmd match {
      case bc:BasicCommand =>
        if(subscriber!=null){ subscriber ! new OutgoingShipCommand(new ShipCommand(id,false,bc.getName)) }  //Send to remote actor
        bc match {
          case CommandUp => up = false
          case CommandDown => down = false
          case CommandLeft => left = false
          case CommandRight => right = false
          case CommandStrafeLeft => strafel = false
          case CommandStrafeRight => strafer = false
          case CommandFire => //
          case e => println("something weird")
        }
    }
  }

}
