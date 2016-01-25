package ch.squan.game.client.model.projectile

import org.newdawn.slick.{GameContainer, Graphics}

/**
  * Created by chris on 22/01/16.
  */
trait Projectile {

  def update(gc: GameContainer, delta: Int):Unit

  def draw(gc: GameContainer, g: Graphics):Unit

  def isExpired:Boolean

}
