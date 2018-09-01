package com.exercise.scala.socialnetwork.model

import com.google.gson.annotations.Expose

import scala.collection.mutable.ArrayBuffer

/**
  * <h1> Profile </h1>
  *
  * A model class of a Profile in the Nukr - Social Network.
  *
  * @version 0.0.1
  */
case class Profile(var _name: String,
                   var _friendSuggestion: Boolean = false) {

  @Expose private var _id: Long = 0
  @Expose private var _friends: ArrayBuffer[Profile] = null

  /**
    * Get method of the private variable _id.
    *
    * @return Long The value of the variable _id.
    */
  def id: Long = _id

  /**
    * Set Method of the variable _id.
    *
    * @param id The new variable _id.
    */
  def id_=(id: Long): Unit = {
    _id = id
  }

  /**
    * Get method of the private variable _name.
    *
    * @return String The value of the variable _name.
    */
  def name: String = _name

  /**
    * Set Method of the variable _name.
    *
    * @param name The new variable _name.
    */
  def name_=(name: String): Unit = {
    _name = name
  }

  /**
    * Get method of the private variable _friendSuggestion.
    *
    * @return Boolean The value of the variable _friendSuggestion.
    */
  def friendSuggestion: Boolean = _friendSuggestion

  /**
    * Set Method of the variable _friendSuggestion.
    *
    * @param friendSuggestion The new variable _friendSuggestion.
    */
  def friendSuggestion_=(friendSuggestion: Boolean): Unit = {
    _friendSuggestion = friendSuggestion
  }

  /**
    * Get method of the private variable _friends.
    *
    * @return Boolean The value of the variable _friends.
    */
  @Expose def friends: ArrayBuffer[Profile] = _friends

  /**
    * Set Method of the variable _friends.
    *
    * @param friends The new variable _friends.
    */
  def friends_=(friends: ArrayBuffer[Profile]): Unit = {
    _friends = friends
  }
}
