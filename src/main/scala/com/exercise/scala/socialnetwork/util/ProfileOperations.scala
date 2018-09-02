package com.exercise.scala.socialnetwork.util

import com.exercise.scala.socialnetwork.model._
import com.exercise.scala.socialnetwork.respository._
import com.google.gson.{Gson, JsonArray, JsonObject}

import scala.collection.mutable.{ArrayBuffer, HashMap, ListMap}

/**
  * <h1>ProfileOperations</h1>
  * The ProfileOperations class that extends the SocialNetworkOperation abtract class implements all operation availabel by the Nukr - Social Network.
  *
  * @version 0.0.1
  */
class ProfileOperations extends SocialNetworkOperation[Profile] {

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
  override def addProfile(a: Profile): String = {
    if (a != null && DataRepository.graph.filter(g => g.id == a.id).isEmpty) {
      DataRepository.graph += a
      s"Profile ${a.name} inserted..."
    }
    else "The profile isn't inserted..."
  }

  /**
    * A method that generates the next id for a new profile.
    *
    * @param a A Profile that needs to generate a new incremental Id in the graph.
    * @return Long The next valid and incremental id in the graph.
    */
  override def generateNewId(): Long = {
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
  override def conectProfile(a: Profile, b: Profile): String = {
    if (a != null && b != null) {
      if (a.id != b.id) {
        if (a.friends != null && a.friends.filter(fp => fp.id == b.id).isEmpty) {
          a.friends += b
          b.friends += a
          s"The profiles ${a.name} and ${b.name} is now connected."
        } else s"The profiles ${a.name} and ${b.name} already friends."
      } else "The profile cannot as self friend."
    } else "The profiles doesn't exist."
  }


  /**
    * A method that returns the non-friends of a profile.
    *
    * @param a
    * @return
    */
  override def notFriends(a: Profile): ArrayBuffer[Profile] = {
    var temp_graph = DataRepository.graph.filterNot(g => g.id == a.id)
    var notFrieds = temp_graph.filter(g => a.friends.filter(af => af.id == g.id).isEmpty && g.friendSuggestion)
    notFrieds
  }

  /**
    * A method that counts the number of mutual friends of a set of non-friend profiles.
    *
    * @param a
    * @return
    */
  override def mutualFriendCounter(a: Profile): ListMap[String, Long] = {
    var count_helper = new HashMap[String, Long]()
    var notFriendsList = notFriends(a)
    notFriendsList.foreach(nf => count_helper.put(nf.name, nf.friends.count(x => a.friends.filter(af => af.id == x.id).nonEmpty)))
    val listMap: ListMap[String, Long] = ListMap(count_helper.filterNot(ch => ch._2 == 0).toSeq.sortWith(_._1 > _._1): _*)
    listMap
  }


  /**
    * A method that performs the recommendation of suggestion friends by the number of mutual friends.
    *
    * @param a The Profile that needs to get the friends suggestion.
    * @return ListMap[String, Long] A sorted ListMap of the name of suggested friends and the number of mutual friends.
    */
  override def friendSuggestion(a: Profile): String = {
    if (a.friends != null && a.friends.nonEmpty) {
      if (a.friendSuggestion) makeSuggestedFriendsJSON(a, mutualFriendCounter(a))
      else s"The profile ${a.name} cannot receive suggested friends because the friend suggestion feature is disabled."
    }
    else s"The profile ${a.name} does not have friends."
  }

  /**
    * A method that enables/disables the friend suggestion recommendation to a specific profile.
    *
    * @param a      The Profile want to change the friendSuggestion to true of false.
    * @param status A Boolean that change the friendSuggestion status to true or false.
    * @return Boolean The status of the execution of this operation.
    */
  override def enableFriendSuggestion(a: Profile, status: Boolean): String = {
    if (a != null) {
      a.friendSuggestion_=(status)
      s"The friend suggestion of profile ${a.name} has been changed."
    } else "The profile does not exists."
  }

  /**
    * A method that make a json output of the list of suggested friends
    *
    * @param a       User Profile
    * @param listMap ListMap of profiles names and the number of mutual friends
    * @return JSON String
    */
  def makeSuggestedFriendsJSON(a: Profile, listMap: ListMap[String, Long]): String = {
    var jsonObject = new JsonObject
    var jsonArray = new JsonArray()
    jsonObject.addProperty("profile_id", a.id)
    jsonObject.addProperty("profile_name", a.name)

    listMap.toList.foreach(sl => {
      var temp = new JsonObject
      temp.addProperty("profile_name", sl._1)
      temp.addProperty("mutual_friends", sl._2)
      jsonArray.add(temp)
    })

    jsonObject.add("suggested_friends", jsonArray)
    new Gson().toJson(jsonObject)
  }
}
