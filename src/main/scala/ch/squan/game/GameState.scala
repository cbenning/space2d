package ch.squan.game

import akka.actor.ActorRef
import ch.squan.game.client.model.ship.{PlayerShip, Ship}
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.World

import scala.collection.concurrent.TrieMap

/**
  * Created by chris on 24/01/16.
  */
class GameState(_subscriber:ActorRef) {

  def subscriber = _subscriber

  var _objects = TrieMap.empty[String,Ship]
  def objects = _objects
  def objects_=(newObjects:TrieMap[String,Ship]) = { _objects = newObjects }

  val gravity = new Vec2(0.0f,0.0f)
  val _world = new World(gravity)
  def world = _world

  var _level:Level = null
  def level = _level
  def level_=(newLevel:Level) = { _level = newLevel }

  var _player:PlayerShip = null
  def player = _player
  def player_=(newPlayer:PlayerShip) = { _player = newPlayer }


}
