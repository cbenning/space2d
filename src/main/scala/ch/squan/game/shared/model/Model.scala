package ch.squan.game.shared.model

import org.newdawn.slick.command.{BasicCommand, Command}


/**
  * Created by chris on 28/01/16.
  */

//case class CmdInit(state:GameState=>Unit)
//case class CmdRender(f:GameState=>Unit)
//case class CmdUpdate(f:GameState=>Unit)
case object CmdStateUpdate

case class OutgoingShipCommand(cmd:ShipCommand)

case class ShipCommand(id:String, pressed:Boolean, cmd:String)