package com.exercise.scala.socialnetwork.util

import scala.collection.mutable.{ArrayBuffer, HashMap, ListMap}

abstract class SocialNetworkOperation[A, B] {

  def makeId(a: A, graph: ArrayBuffer[B]): Long

  def addProfile(a: A, graph: ArrayBuffer[B]): Boolean

  def conectProfile(a: A, b: A): Boolean

  def friendSuggestion(a: A, graph: ArrayBuffer[B]): ListMap[String, Long]

  def enableFriendSuggestion(a: A, status: Boolean, graph: ArrayBuffer[B]): Boolean
}
