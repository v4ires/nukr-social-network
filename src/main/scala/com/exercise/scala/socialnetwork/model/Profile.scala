package com.exercise.scala.socialnetwork.model

import com.google.gson.annotations.Expose

import scala.collection.mutable.ArrayBuffer

case class Profile() {

  @Expose private var _id: Long = 0
  @Expose private var _friends: ArrayBuffer[Profile] = new ArrayBuffer[Profile]()

  private var _name: String = ""
  private var _friendSuggestion = false

  def id: Long = _id

  def name: String = _name

  def friendSuggestion: Boolean = _friendSuggestion

  @Expose def friends: ArrayBuffer[Profile] = _friends

  def id_=(id: Long): Unit = {
    _id = id
  }

  def name_=(name: String): Unit = {
    _name = name
  }

  def friendSuggestion_=(friendSuggestion: Boolean): Unit = {
    _friendSuggestion = friendSuggestion
  }

  def friends_=(friends: ArrayBuffer[Profile]): Unit = {
    _friends = friends
  }
}
