package com.exercise.scala.socialnetwork.controller

import com.exercise.scala.socialnetwork.model.Profile
import com.exercise.scala.socialnetwork.respository.DataRepository
import com.exercise.scala.socialnetwork.util.ProfileOperations
import com.google.gson.{Gson, JsonObject, JsonParser}
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(path = Array("/api"))
class AppController {

  class FriendLoopException extends Exception("the same profile cannot add as friend")

  /**
    *
    * @param profile
    * @return
    */
  @PostMapping(path = Array("/add/profile"), produces = Array("text/plain"), consumes = Array("application/json"))
  def addProfile(@RequestBody profile: String): String = {
    val new_profile = new Gson().fromJson(profile, classOf[Profile])
    val check: Boolean = ProfileOperations.addProfile(new_profile)
    if (check) s"Profile ${new_profile.name} inserted..."
    else s"Profile ${new_profile.name} isn't inserted..."
  }

  /**
    *
    * @param raw_json
    * @return
    */
  @PostMapping(path = Array("/connect/profile"), produces = Array("text/plain"), consumes = Array("application/json"))
  def connectProfile(@RequestBody raw_json: String): String = {

    try {
      val jsonObject = new JsonParser().parse(raw_json).getAsJsonObject
      val p1 = ProfileOperations.findProfile(jsonObject.get("_id1").getAsLong)
      val p2 = ProfileOperations.findProfile(jsonObject.get("_id2").getAsLong)

      if (p1 != null && p2 != null) {
        if (p1.id != p2.id) {
          ProfileOperations.conectProfile(p1, p2)
          s"the profiles ${p1.name} and ${p2.name} is now connected..."
        } else {
          throw new FriendLoopException
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
    *
    * @param raw_json
    * @return
    */
  @PostMapping(path = Array("/suggested/profile"), produces = Array("text/plain"), consumes = Array("application/json"))
  def suggestedFriends(@RequestBody raw_json: String): String = {
    val json_input = new JsonParser().parse(raw_json).getAsJsonObject
    val profile = ProfileOperations.findProfile(json_input.get("_id1").getAsLong)
    val listMap = ProfileOperations.friendSuggestion(profile)
    listMap.toString()
  }

  /**
    *
    * @return
    */
  @GetMapping(path = Array("/show/profile"))
  def showProfile: String = {
    if (!DataRepository.graph.isEmpty) new Gson().toJson(DataRepository.graph)
    else "social network empty"
  }

  /**
    *
    * @return
    */
  @PostMapping(path = Array("/edit/profile/friend/suggestion"), produces = Array("text/plain"), consumes = Array("application/json"))
  def friendSuggestion(@RequestBody raw_json: String): Boolean = {
    val json_input: JsonObject = new JsonParser().parse(raw_json).getAsJsonObject
    val profile: Profile = ProfileOperations.findProfile(json_input.get("_id1").getAsLong)
    val status: Boolean = json_input.get("status").getAsBoolean
    ProfileOperations.enableFriendSuggestion(profile, status)
  }
}
