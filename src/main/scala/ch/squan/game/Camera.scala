package ch.squan.game

import ch.squan.game.model.ship.PlayerShip

/**
  * Created by chris on 23/01/16.
  */
object Camera {

  var _player:PlayerShip = null
  var _h = 1
  var _w = 1

  def follow(player:PlayerShip):Unit = { _player = player }

  def setSize(w:Int,h:Int):Unit = { _w = w; _h = h }

  def x = _player.centerX

  def y = _player.centerY

  def w = _w

  def h = _h

}

