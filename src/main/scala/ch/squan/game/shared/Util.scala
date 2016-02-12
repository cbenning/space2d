package ch.squan.game.shared

/**
  * Created by chris on 30/01/16.
  */
trait Util {

  def PHYSICS_SCALE = 0.1f

  def toPhysX(x:Float):Float = { x * PHYSICS_SCALE }
  def toPhysY(y:Float):Float = { -y * PHYSICS_SCALE }
  def toPhysW(w:Float):Float = { w * PHYSICS_SCALE }
  def toPhysH(h:Float):Float = { -h * PHYSICS_SCALE }
  def toPhys(h:Float):Float = { h * PHYSICS_SCALE }

  def toGraphX(x:Float):Float = { x / PHYSICS_SCALE }
  def toGraphY(y:Float):Float = { -y / PHYSICS_SCALE }
  def toGraphW(w:Float):Float = { w / PHYSICS_SCALE }
  def toGraphH(h:Float):Float = { -h / PHYSICS_SCALE }
  def toGraph(h:Float):Float = { h / PHYSICS_SCALE }

}
