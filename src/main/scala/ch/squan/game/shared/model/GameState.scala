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

//  private val _lock: ReadWriteLock = new ReentrantReadWriteLock()
//  def lock = _lock

  val update = new AtomicReference[StateUpdate]()
//  def update = _update
//  def update_=(newState:StateUpdate) = _update = Some(newState)

  var _camera:Camera = new Camera
  def camera = _camera

  var _subscriber:ActorRef = null
  def subscriber = _subscriber
  def subscriber_=(newSubscriber:ActorRef) = _subscriber=newSubscriber

  var _objects = TrieMap.empty[String,Ship]
  def objects = _objects
//  def objects_=(newObjects:TrieMap[String,Ship]) = { _objects = newObjects }

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
