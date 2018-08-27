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

  val ops: ProfileOperations = new ProfileOperations

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
      ops.addProfile(new_profile)
    } catch {
      case e: Exception => "ERRO 500: Internal server error."
    }
  }

  /**
    * A Post Method that receives a JSON file with two profiles id and makes the friendship between then.
    *
    * @param raw_json A JSON String with the content of two profile's id (_id1 and _id2).
    * @return String The status of this operation.
    */
  @PostMapping(path = Array("/connect/profile"), produces = Array("text/plain"), consumes = Array("application/json"))
  def connectProfile(@RequestBody body: String): String = {
    try {
      val jsonObject = new JsonParser().parse(body).getAsJsonObject
      val p1 = ops.findProfile(jsonObject.get("_profile_id_1").getAsLong)
      val p2 = ops.findProfile(jsonObject.get("_profile_id_2").getAsLong)
      ops.conectProfile(p1, p2)
    } catch {
      case e: NoSuchElementException => "Profile not found."
      case e: Exception => "ERRO 500: Internal server error."
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
      val profile = ops.findProfile(json_input.get("_profile_id").getAsLong)
      ops.friendSuggestion(profile)
    } catch {
      case e: NoSuchElementException => "Profile not found."
      case e: Exception => "ERRO 500: Internal server error."
    }
  }

  /**
    * A Post Method that permits to enable or disable the friend suggestion operation of a specific profile.
    *
    * @return The status of this operation.
    */
  @PostMapping(path = Array("/edit/profile/friend/suggestion"), produces = Array("text/plain"), consumes = Array("application/json"))
  def enableFriendSuggestion(@RequestBody raw_json: String): String = {
    try {
      val json_input: JsonObject = new JsonParser().parse(raw_json).getAsJsonObject
      val profile: Profile = ops.findProfile(json_input.get("_profile_id").getAsLong)
      val new_status: Boolean = json_input.get("status").getAsBoolean
      ops.enableFriendSuggestion(profile, new_status)
    } catch {
      case e: NoSuchElementException => "Profile not found."
      case e: Exception => "ERRO 500: Internal server error."
    }
  }
}
