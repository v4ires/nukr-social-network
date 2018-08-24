package com.exercise.scala.socialnetwork.controller

import com.exercise.scala.socialnetwork.model.Profile
import com.exercise.scala.socialnetwork.util.ProfileOperations
import com.google.gson.{Gson, JsonParser}
import org.springframework.web.bind.annotation._

import scala.collection.mutable.ArrayBuffer

@RestController
@RequestMapping(path = Array("/api"))
class AppController {

  var graph = new ArrayBuffer[Profile]()

  class FriendLoopException extends Exception("the same profile cannot add as friend")

  @GetMapping(path = Array("/hello"))
  def hello: String = "hello world"

  /**
    *
    * @param profile
    * @return
    */
  @PostMapping(path = Array("/add/profile"), produces = Array("text/plain"), consumes = Array("application/json"))
  def addProfile(@RequestBody profile: String): String = {
    val new_profile = new Gson().fromJson(profile, classOf[Profile])
    val check: Boolean = ProfileOperations.addProfile(new_profile, graph)
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
      val parser = new JsonParser
      val jsonObject = parser.parse(raw_json).getAsJsonObject
      val p1 = graph.find(p => p.id == jsonObject.get("_id1").getAsLong).get
      val p2 = graph.find(p => p.id == jsonObject.get("_id2").getAsLong).get

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

  @PostMapping(path = Array("/suggested/profile"), produces = Array("text/plain"), consumes = Array("application/json"))
  def suggestedFriends(@RequestBody raw_json: String): String = {
    val json_input = new JsonParser().parse(raw_json).getAsJsonObject
    val profile = graph.find(p => p.id == json_input.get("_id1").getAsLong).get
    val listMap = ProfileOperations.friendSuggestion(profile, graph)
    listMap.toString()
  }

  @GetMapping(path = Array("/show/profile"))
  def showProfile: String = {
    if (!graph.isEmpty) new Gson().toJson(graph)
    else "social network empty"
  }
}
