package com.exercise.scala.socialnetwork.respository

import com.exercise.scala.socialnetwork.model.Profile

import scala.collection.mutable.ArrayBuffer

object DataRepository {
  var _graph = new ArrayBuffer[Profile]()

  def graph: ArrayBuffer[Profile] = {
    this.synchronized {
      _graph
    }
  }

  def graph_=(graph: ArrayBuffer[Profile]): Unit = {
    this.synchronized {
      _graph = graph
    }
  }
}
