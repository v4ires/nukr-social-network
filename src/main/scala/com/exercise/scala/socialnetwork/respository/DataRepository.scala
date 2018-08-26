package com.exercise.scala.socialnetwork.respository

import com.exercise.scala.socialnetwork.model.Profile

import scala.collection.mutable.ArrayBuffer

/**
  * <h1> DataRepository </h1>
  *
  * A Data Repository class of Nukr - Social Network.
  *
  * @version 0.0.1
  */
object DataRepository {

  var _graph = new ArrayBuffer[Profile]()

  /**
    * Get method of the private variable _graph.
    *
    * @return ArrayBuffer[Profile] The value of the variable _graph.
    */
  def graph: ArrayBuffer[Profile] = {
    this.synchronized {
      _graph
    }
  }

  /**
    * Set Method of the variable _graph.
    *
    * @param ArrayBuffer [Profile] The new variable _graph.
    */
  def graph_=(graph: ArrayBuffer[Profile]): Unit = {
    this.synchronized {
      _graph = graph
    }
  }
}
