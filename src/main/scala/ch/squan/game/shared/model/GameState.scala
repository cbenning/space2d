package ch.squan.game.shared.model

import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.{ReentrantReadWriteLock, ReadWriteLock}

import akka.actor.ActorRef
import ch.squan.game.{StateUpdate, Camera, Level}
import ch.squan.game.client.model.ship.{PlayerShip, Ship}
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.World

import scala.collection.concurrent.TrieMap

/**
  * Created by chris on 24/01/16.
  */
class GameState {

  val update = new AtomicReference[StateUpdate]()

  var _camera:Camera = new Camera
  def camera = _camera

  var _objects = TrieMap.empty[String,Ship]
  def objects = _objects

  var refreshFlag:Boolean = false

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
