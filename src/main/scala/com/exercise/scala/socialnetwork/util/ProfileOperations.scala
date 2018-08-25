package com.exercise.scala.socialnetwork.util

import com.exercise.scala.socialnetwork.model._
import com.exercise.scala.socialnetwork.respository._

import scala.collection.mutable.{HashMap, ListMap}

object ProfileOperations extends SocialNetworkOperation[Profile] {

  /**
    *
    * @param id
    * @return
    */
  override def findProfile(id: Long): Profile = {
    DataRepository.graph.find(p => p.id == id).get
  }

  /**
    *
    * @param a
    * @return
    */
  override def makeId(a: Profile): Long = {
    if (DataRepository.graph.isEmpty) 1L
    else DataRepository.graph.last.id + 1L
  }

  /**
    *
    * @param a
    * @return
    */
  override def addProfile(a: Profile): Boolean = {
    if (a != null && DataRepository.graph.filter(g => g.id == a.id).isEmpty) {
      a.id_=(makeId(a))
      DataRepository.graph += a
      true
    }
    else false
  }

  /**
    *
    * @param a
    * @param b
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
    * @return
    */
  override def friendSuggestion(a: Profile): ListMap[String, Long] = {
    var count_helper = new HashMap[String, Long]()
    var temp_graph = DataRepository.graph.clone.filterNot(g => g.id == a.id)
    var notFrieds = temp_graph.filter(g => a.friends.filter(af => af.id == g.id).isEmpty && g.friendSuggestion)
    notFrieds.foreach(nf => count_helper.put(nf.name, nf.friends.count(x => a.friends.filter(af => af.id == x.id).nonEmpty)))
    ListMap(count_helper.toSeq.sortWith(_._2 > _._2): _*)
  }

  /**
    *
    * @param a
    * @param status
    * @return
    */
  override def enableFriendSuggestion(a: Profile, status: Boolean): Boolean = {
    a.friendSuggestion_=(status)
    true
  }
}
