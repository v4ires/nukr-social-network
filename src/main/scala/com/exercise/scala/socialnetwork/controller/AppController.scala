package com.exercise.scala.socialnetwork.controller

import com.exercise.scala.socialnetwork.model.Profile
import com.exercise.scala.socialnetwork.util.ProfileOperations
import com.google.gson.{Gson, JsonParser}
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation._

import scala.collection.mutable.ArrayBuffer

/**
  * <h1> AppController </h1>
  * A Spring RestController class that provides the endpoints of the Nukr - Social Network.
  *
  * @version 0.0.1
  */
@RestController
@RequestMapping(path = Array("/api"))
class AppController {

  def log = LoggerFactory.getLogger(this.getClass.getName.replace("$", ""))

  val ops: ProfileOperations = new ProfileOperations

  /**
    * A Post Method that receives of a profile in JSON and adds in the social network (graph).
    *
    * @param profile A JSON string with the content of a new profile.
    * @return String The status of this operation.
    */
  @PostMapping(path = Array("/add/profile"), produces = Array("text/plain"), consumes = Array("application/json"))
  def addProfile(@RequestBody profile: String): String = {
    var msg = ""
    try {
      val new_profile = new Gson().fromJson(profile, classOf[Profile])
      new_profile.id_=(ops.generateNewId())
      new_profile.friends_=(new ArrayBuffer[Profile]())
      msg = ops.addProfile(new_profile)
      log.info(msg)
      msg
    } catch {
      case e: Exception => {
        msg = "ERRO 500: Internal server error."
        log.error(msg)
        msg
      }
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
    var msg = ""
    try {
      val jsonObject = new JsonParser().parse(body).getAsJsonObject
      if (jsonObject.get("_profile_id_1") != null && jsonObject.get("_profile_id_2") != null && jsonObject.keySet().toArray.length == 2) {
        val p1 = ops.findProfile(jsonObject.get("_profile_id_1").getAsLong)
        val p2 = ops.findProfile(jsonObject.get("_profile_id_2").getAsLong)
        msg = ops.conectProfile(p1, p2)
      } else msg = s"The JSON Key is invalid."
      log.info(msg)
      msg
    } catch {
      case e: NoSuchElementException => {
        msg = "Profile not found."
        log.error(msg)
        msg
      }
      case e: Exception => {
        msg = "ERRO 500: Internal server error."
        log.error(msg)
        msg
      }
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
    var msg = ""
    try {
      val jsonObject = new JsonParser().parse(raw_json).getAsJsonObject
      if (jsonObject.get("_profile_id") != null && jsonObject.keySet().toArray.length == 1) {
        val profile = ops.findProfile(jsonObject.get("_profile_id").getAsLong)
        msg = ops.friendSuggestion(profile)
      } else msg = s"The JSON Key is invalid."
      log.info(msg)
      msg
    } catch {
      case e: NoSuchElementException => {
        msg = "Profile not found."
        log.error(msg)
        msg
      }
      case e: Exception => {
        msg = "ERRO 500: Internal server error."
        log.error(msg)
        msg
      }
    }
  }

  /**
    * A Post Method that permits to enable or disable the friend suggestion operation of a specific profile.
    *
    * @return The status of this operation.
    */
  @PostMapping(path = Array("/edit/profile/friend/suggestion"), produces = Array("text/plain"), consumes = Array("application/json"))
  def enableFriendSuggestion(@RequestBody raw_json: String): String = {
    var msg = ""
    try {
      val jsonObject = new JsonParser().parse(raw_json).getAsJsonObject
      if (jsonObject.get("_profile_id") != null && jsonObject.get("_friend_suggestion") != null && jsonObject.keySet().toArray.length == 2) {
        val profile: Profile = ops.findProfile(jsonObject.get("_profile_id").getAsLong)
        val new_status: Boolean = jsonObject.get("_friend_suggestion").getAsBoolean
        msg = ops.enableFriendSuggestion(profile, new_status)
      } else msg = s"The JSON Key is invalid."
      log.info(msg)
      msg
    } catch {
      case e: NoSuchElementException => {
        msg = "Profile not found."
        log.error(msg)
        msg
      }
      case e: Exception => {
        msg = "ERRO 500: Internal server error."
        log.error(msg)
        msg
      }
    }
  }
}
