package ch.squan.game.client.model.command

import org.newdawn.slick.command.BasicCommand

/**
  * Created by chris on 24/01/16.
  */

//case class InputCommand(name:String) extends BasicCommand(name)
case object CommandUp extends BasicCommand("up")
case object CommandDown extends BasicCommand("down")
case object CommandFire extends BasicCommand("fire")
case object CommandLeft extends BasicCommand("left")
case object CommandRight extends BasicCommand("right")
case object CommandStrafeRight extends BasicCommand("straferight")
case object CommandStrafeLeft extends BasicCommand("strafeleft")
