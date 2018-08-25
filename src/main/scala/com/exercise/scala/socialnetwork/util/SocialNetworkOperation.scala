package com.exercise.scala.socialnetwork.util

import scala.collection.mutable.ListMap

abstract class SocialNetworkOperation[A] {

  def findProfile(id: Long): A

  def makeId(a: A): Long

  def addProfile(a: A): Boolean

  def conectProfile(a: A, b: A): Boolean

  def friendSuggestion(a: A): ListMap[String, Long]

  def enableFriendSuggestion(a: A, status: Boolean): Boolean
}
