package com.exercise.scala.socialnetwork.util

import com.exercise.scala.socialnetwork.model._

import scala.collection.mutable.{ArrayBuffer, HashMap, ListMap}

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
    if (a != null && graph.filter(g => g.id == a.id).isEmpty) {
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
    if (a.friends.filter(fp => fp.id == b.id).isEmpty) {
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
  override def friendSuggestion(a: Profile, graph: ArrayBuffer[Profile]): ListMap[String, Long] = {
    var count_helper = new HashMap[String, Long]()
    var notFrieds = graph.clone.-=(a).
      filter(g => a.friends.filter(af => af.id == g.id && g.friendSuggestion).isEmpty)
    notFrieds
      .foreach(nf => count_helper.put(nf.name, nf.friends.count(x => !a.friends.filter(af => af.id == x.id).isEmpty)))
    ListMap(count_helper.toSeq.sortWith(_._1 > _._1): _*)
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
