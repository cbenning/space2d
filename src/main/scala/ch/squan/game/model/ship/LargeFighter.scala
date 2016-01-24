package ch.squan.game.model.ship

import org.jbox2d.dynamics.World

/**
  * Created by chris on 23/01/16.
  */
class LargeFighter(world:World,x:Float,y:Float,angle:Float)
  extends Ship(world,x,y,angle,60.0f,120.0f,"large.png") {
}
