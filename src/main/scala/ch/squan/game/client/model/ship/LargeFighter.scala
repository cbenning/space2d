package ch.squan.game.client.model.ship

import ch.squan.game.shared.model.GameState
import org.jbox2d.dynamics.World

/**
  * Created by chris on 23/01/16.
  */
class LargeFighter(state:GameState, x:Float, y:Float, angle:Float)
  extends Ship(state,x,y,angle,0.01f,0.02f,0.01f,1.0f,1.0f,"large.png") {
}
