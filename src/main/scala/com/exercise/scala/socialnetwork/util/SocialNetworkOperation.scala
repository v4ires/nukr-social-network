package com.exercise.scala.socialnetwork.util

import scala.collection.mutable.ArrayBuffer

abstract class SocialNetworkOperation[A, B] {

  def makeId(a: A, graph: ArrayBuffer[B]): Long

  def addProfile(a: A, graph: ArrayBuffer[B]): Boolean

  def conectProfile(a: A, b: A): Boolean

  def friendSuggestion(a: A, graph: ArrayBuffer[B]): ArrayBuffer[B]

  def enableFriendSuggestion(a: A, status: Boolean, graph: ArrayBuffer[B]): Boolean
}
