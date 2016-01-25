package ch.squan.game

import org.jbox2d.common.Vec2

/**
  * Created by chris on 23/01/16.
  */
case class ClientConnect(id:String)
case class ClientConnected(id:String)

//case class GameState(bgImage:String,levelWidth:Int,levelHeigh:Int)

case class ShipState(speed:Float, turning:Float, imgPath:String)
case class PhysicsState(x:Float, y:Float, angle:Float, linearVelocity:Vec2, angularVelocity:Float)
case class ShipStateUpdate(id:String, ship:ShipState, physics:PhysicsState)
case class StateUpdate(objects:Iterable[ShipStateUpdate])
