package com.exercise.scala.socialnetwork.util

import com.exercise.scala.socialnetwork.model._
import com.exercise.scala.socialnetwork.respository._

import scala.collection.mutable.{HashMap, ListMap}

/**
  * <h1>ProfileOperations</h1>
  * The ProfileOperations class that extends the SocialNetworkOperation abtract class implements all operation availabel by the Nukr - Social Network.
  *
  * @version 0.0.1
  */
object ProfileOperations extends SocialNetworkOperation[Profile] {

  /**
    * A method that requests to the social network (graph) a profile by id.
    *
    * @param id This is the id of profile you can find.
    * @return Profile A instance of class Profile with the same id.
    * @exception NoSuchElementException If the Profile id is not found in the graph.
    * @see NoSuchElementException
    */
  @throws(classOf[NoSuchElementException])
  override def findProfile(id: Long): Profile = {
    DataRepository.graph.find(p => p.id == id).get
  }

  /**
    * A method that requests to the social network (graph) a profile by name
    *
    * @param name The name of specific profile in the graph.
    * @return Profile A instance of class Profile with the same name.
    * @exception NoSuchElementException If the Profile name is not found in the graph.
    * @see NoSuchElementException
    */
  @throws(classOf[NoSuchElementException])
  override def findProfile(name: String): Profile = {
    DataRepository.graph.find(p => p.name == name).get
  }

  /**
    * A method that performs to add a new profile in the social network (graph).
    *
    * @param a A new Profile that you want to add in the graph.
    * @return Boolean The status of the execution of this operation.
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
    * A method that generates the next id for a new profile.
    *
    * @param a A Profile that needs to generate a new incremental Id in the graph.
    * @return Long The next valid and incremental id in the graph.
    */
  override def makeId(a: Profile): Long = {
    if (DataRepository.graph.isEmpty) 1L
    else DataRepository.graph.last.id + 1L
  }

  /**
    * A method that connects to profiles as a friend.
    *
    * @param a The first Profile.
    * @param b The Second Profile.
    * @return Boolean The status of the execution of this operation.
    */
  override def conectProfile(a: Profile, b: Profile): Boolean = {
    if (a.friends.filter(fp => fp.id == b.id).isEmpty) {
      a.friends += b
      b.friends += a
      true
    } else false
  }

  /**
    * A method that performs the recommendation of suggestion friends by the number of mutual friends.
    *
    * @param a The Profile that needs to get the friends suggestion.
    * @return ListMap[String, Long] A sorted ListMap of the name of suggested friends and the number of mutual friends.
    */
  override def friendSuggestion(a: Profile): ListMap[String, Long] = {
    var count_helper = new HashMap[String, Long]()
    var temp_graph = DataRepository.graph.clone.filterNot(g => g.id == a.id)
    var notFrieds = temp_graph.filter(g => a.friends.filter(af => af.id == g.id).isEmpty && g.friendSuggestion)
    notFrieds.foreach(nf => count_helper.put(nf.name, nf.friends.count(x => a.friends.filter(af => af.id == x.id).nonEmpty)))
    ListMap(count_helper.toSeq.sortWith(_._2 > _._2): _*)
  }

  /**
    * A method that enables/disables the friend suggestion recommendation to a specific profile.
    *
    * @param a      The Profile want to change the friendSuggestion to true of false.
    * @param status A Boolean that change the friendSuggestion status to true or false.
    * @return Boolean The status of the execution of this operation.
    */
  override def enableFriendSuggestion(a: Profile, status: Boolean): Boolean = {
    a.friendSuggestion_=(status)
    true
  }
}
