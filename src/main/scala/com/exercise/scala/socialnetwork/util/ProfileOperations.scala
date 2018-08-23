package com.exercise.scala.socialnetwork.util

import com.exercise.scala.socialnetwork.model._

import scala.collection.mutable.{ArrayBuffer, HashMap}

object ProfileOperations extends SocialNetworkOperation[Profile, Profile] {

  /**
    *
    * @param a
    * @param graph
    * @return
    */
  override def makeId(a: Profile, graph: ArrayBuffer[Profile]): Long = {
    if (graph.isEmpty) 1L
    else graph.last.id + 1L
  }

  /**
    *
    * @param a
    * @param graph
    * @return
    */
  override def addProfile(a: Profile, graph: ArrayBuffer[Profile]): Boolean = {
    if (a != null) {
      a.id_=(makeId(a, graph))
      graph += a
      true
    }
    else false
  }

  /**
    *
    * @param a
    * @param b
    * @param graph
    * @return
    */
  override def conectProfile(a: Profile, b: Profile): Boolean = {
    if ((!a.friends.contains(b) && !b.friends.contains(a))) {
      a.friends += b
      b.friends += a
      true
    } else false
  }

  /**
    *
    * @param a
    * @param graph
    * @return
    */
  override def friendSuggestion(a: Profile, graph: ArrayBuffer[Profile]): ArrayBuffer[Profile] = {
    val friends = a.friends
    val count_helper = new HashMap[Profile, Long]()
    val notFriends = graph.filterNot(Set(friends.toSet, a))
    notFriends.foreach(nf => {
      count_helper.put(nf, nf.friends.count(x => a.friends.contains(x)))
    })
    //map.keySortedSet.to[SortedSet]
    null
  }

  /**
    *
    * @param a
    * @param status
    * @param graph
    * @return
    */
  override def enableFriendSuggestion(a: Profile, status: Boolean, graph: ArrayBuffer[Profile]): Boolean = {
    false
  }
}
