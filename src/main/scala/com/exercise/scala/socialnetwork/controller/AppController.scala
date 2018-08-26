package com.exercise.scala.socialnetwork.controller

import com.exercise.scala.socialnetwork.model.Profile
import com.exercise.scala.socialnetwork.util.ProfileOperations
import com.google.gson.{Gson, JsonObject, JsonParser}
import org.springframework.web.bind.annotation._

/**
  * <h1> AppController </h1>
  * A Spring RestController class that provides the endpoints of the Nukr - Social Network.
  *
  * @version 0.0.1
  */
@RestController
@RequestMapping(path = Array("/api"))
class AppController {

  /**
    * A Post Method that receives of a profile in JSON and adds in the social network (graph).
    *
    * @param profile A JSON string with the content of a new profile.
    * @return String The status of this operation.
    */
  @PostMapping(path = Array("/add/profile"), produces = Array("text/plain"), consumes = Array("application/json"))
  def addProfile(@RequestBody profile: String): String = {
    try {
      val new_profile = new Gson().fromJson(profile, classOf[Profile])
      val check: Boolean = ProfileOperations.addProfile(new_profile)
      if (check) s"Profile ${new_profile.name} inserted..."
      else s"Profile ${new_profile.name} isn't inserted..."
    } catch {
      case e: Exception => "ERRO 500"
    }
  }

  /**
    * A Post Method that receives a JSON file with two profiles id and makes the friendship between then.
    *
    * @param raw_json A JSON String with the content of two profile's id (_id1 and _id2).
    * @return String The status of this operation.
    */
  @PostMapping(path = Array("/connect/profile"), produces = Array("text/plain"), consumes = Array("application/json"))
  def connectProfile(@RequestBody raw_json: String): String = {

    try {
      val jsonObject = new JsonParser().parse(raw_json).getAsJsonObject
      val p1 = ProfileOperations.findProfile(jsonObject.get("_id1").getAsLong)
      val p2 = ProfileOperations.findProfile(jsonObject.get("_id2").getAsLong)

      if (p1 != null && p2 != null) {
        if (p1.friends.filter(p => p.id == p2.id).nonEmpty) {
          s"${p1.name} and ${p2.name} already friends"
        } else {
          if (p1.id != p2.id) {
            ProfileOperations.conectProfile(p1, p2)
            s"the profiles ${p1.name} and ${p2.name} is now connected..."
          } else {
            throw new FriendLoopException
          }
        }
      } else {
        s"the profile id = ${p1.id} or/and ${p2.id} doesn't exist..."
      }
    } catch {
      case e: FriendLoopException => e.getMessage
      case e: Exception => "ERRO 500"
    }
  }

  /**
    * A Post Method that gets the suggested friends of a specific profile.
    *
    * @param raw_json A JSON string with the profile's id of the profile that requests the suggested friends.
    * @return String The status of this operation.
    */
  @PostMapping(path = Array("/suggested/profile"), produces = Array("text/plain"), consumes = Array("application/json"))
  def suggestedFriends(@RequestBody raw_json: String): String = {
    try {
      val json_input = new JsonParser().parse(raw_json).getAsJsonObject
      val profile = ProfileOperations.findProfile(json_input.get("_id1").getAsLong)
      val listMap = ProfileOperations.friendSuggestion(profile)
      listMap.toString()
    } catch {
      case e: NoSuchElementException => "profile not found"
      case e: Exception => "Error 500"
    }
  }

  /**
    * A Post Method that permits to enable or disable the friend suggestion operation of a specific profile.
    *
    * @return The status of this operation.
    */
  @PostMapping(path = Array("/edit/profile/friend/suggestion"), produces = Array("text/plain"), consumes = Array("application/json"))
  def enableFriendSuggestion(@RequestBody raw_json: String): String = {
    val json_input: JsonObject = new JsonParser().parse(raw_json).getAsJsonObject
    val profile: Profile = ProfileOperations.findProfile(json_input.get("_id1").getAsLong)
    val status: Boolean = json_input.get("status").getAsBoolean
    val operation_status = ProfileOperations.enableFriendSuggestion(profile, status)
    if (operation_status) s"the friend suggestion of profile ${profile.name} has been changed"
    else s"the friend suggestion of profile ${profile.name} isn't changed"
  }

  class FriendLoopException extends Exception("the same profile cannot add as friend")
}
