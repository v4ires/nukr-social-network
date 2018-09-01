package com.exercise.scala.socialnetwork.util

import com.exercise.scala.socialnetwork.model.Profile

import scala.collection.mutable.{ArrayBuffer, ListMap}

/**
  * <h1>SocialNetworkOperation</h1>
  * A abstract class of the all available operation in the Nukr - Social Network.
  *
  * @tparam A Generic Class type
  * @version 0.0.1
  */
abstract class SocialNetworkOperation[A] {

  /**
    * A method that requests to the social network (graph) a profile by id.
    *
    * @param id This is the id of profile you can find.
    * @return Profile A instance of class Profile with the same id.
    * @exception NoSuchElementException If the Profile id is not found in the graph.
    * @see NoSuchElementException
    */
  @throws(classOf[NoSuchElementException])
  def findProfile(id: Long): A

  /**
    * A method that requests to the social network (graph) a profile by name.
    *
    * @param name The name of specific profile in the graph.
    * @return Profile A instance of class Profile with the same name.
    * @exception NoSuchElementException If the Profile name is not found in the graph.
    * @see NoSuchElementException
    */
  @throws(classOf[NoSuchElementException])
  def findProfile(name: String): A


  /**
    * A method that generates the next id for a new profile.
    *
    * @param a A Profile that needs to generate a new incremental Id in the graph.
    * @return Long The next valid and incremental id in the graph.
    */
  def generateNewId(): Long

  /**
    * A method that performs to add a new profile in the social network (graph).
    *
    * @param a A new Profile that you want to add in the graph.
    * @return Boolean The status of the execution of this operation.
    */
  def addProfile(a: A): String

  /**
    * A method that connects to profiles as a friend.
    *
    * @param a The first Profile.
    * @param b The Second Profile.
    * @return Boolean The status of the execution of this operation [true or false].
    */
  def conectProfile(a: A, b: A): String

  /**
    * A method that returns the non-friends of a profile.
    *
    * @param a
    * @return
    */
  def notFriends(a: A): ArrayBuffer[A]

  /**
    * A method that counts the number of mutual friends of a set of non-friend profiles.
    *
    * @param a
    * @return
    */
  def mutualFriendCounter(a: A): ListMap[String, Long]

  /**
    * A method that performs the recommendation of suggestion friends by the number of mutual friends.
    *
    * @param a The Profile that needs to get the friends suggestion.
    * @return ListMap[String, Long] A sorted ListMap of the name of suggested friends and the number of mutual friends.
    */
  def friendSuggestion(a: A): String

  /**
    * A method that enables/disables the friend suggestion recommendation to a specific profile.
    *
    * @param a      The Profile want to change the friendSuggestion to true of false.
    * @param status A Boolean that change the friendSuggestion status to true or false.
    * @return Boolean The status of the execution of this operation.
    */
  def enableFriendSuggestion(a: A, status: Boolean): String

  /**
    * A method that make a json output of the list of suggested friends
    *
    * @param a       User Profile
    * @param listMap ListMap of profiles names and the number of mutual friends
    * @return JSON String
    */
  def makeSuggestedFriendsJSON(a: Profile, listMap: ListMap[String, Long]): String
}
