import com.exercise.scala.socialnetwork.model._
import com.exercise.scala.socialnetwork.respository.DataRepository
import com.exercise.scala.socialnetwork.util._
import junit.framework.TestCase
import org.junit.Assert

import scala.collection.mutable.ArrayBuffer

class CaseTests extends TestCase {

  var ops: ProfileOperations = _

  override def setUp: Unit = {
    ops = new ProfileOperations
  }

  def testAddProfile: Unit = {
    DataRepository.graph.clear()

    var p = new Profile(_name = "Vinícius Aires Barros", _friendSuggestion = true)
    p.id_=(ops.generateNewId())

    Assert.assertEquals(ops.addProfile(p), s"Profile ${p.name} inserted...")
    Assert.assertEquals(ops.addProfile(null), "The profile isn't inserted...")
  }

  def testConnectProfiles: Unit = {
    DataRepository.graph.clear()

    var p1 = new Profile(_name = "Profile 1", _friendSuggestion = true)
    p1.id_=(ops.generateNewId())
    p1.friends_=(new ArrayBuffer[Profile]())
    ops.addProfile(p1)

    var p2 = new Profile(_name = "Profile 2", _friendSuggestion = true)
    p2.id_=(ops.generateNewId())
    p2.friends_=(new ArrayBuffer[Profile]())
    ops.addProfile(p2)

    Assert.assertEquals(ops.conectProfile(p1, p2), s"The profiles ${p1.name} and ${p2.name} is now connected.")

    Assert.assertEquals(ops.conectProfile(p1, p2), s"The profiles ${p1.name} and ${p2.name} already friends.")

    Assert.assertEquals(ops.conectProfile(p2, p1), s"The profiles ${p2.name} and ${p1.name} already friends.")

    Assert.assertEquals(ops.conectProfile(p1, p1), "The profile cannot as self friend.")

    Assert.assertEquals(ops.conectProfile(p1, null), "The profiles doesn't exist.")

    Assert.assertEquals(ops.conectProfile(null, p2), "The profiles doesn't exist.")
  }

  def testSuggestedFriends(): Unit = {
    DataRepository.graph.clear()

    var p1 = new Profile(_name = "1", _friendSuggestion = true)
    var p2 = new Profile(_name = "2", _friendSuggestion = true)
    var p3 = new Profile(_name = "3", _friendSuggestion = true)
    var p4 = new Profile(_name = "4", _friendSuggestion = true)
    var p5 = new Profile(_name = "5", _friendSuggestion = true)

    List(p1, p2, p3, p4, p5).foreach(p => {
      p.id_=(ops.generateNewId())
      p.friends_=(new ArrayBuffer[Profile]())
      ops.addProfile(p)
    })

    ops.conectProfile(p1, p2)
    ops.conectProfile(p1, p3)
    ops.conectProfile(p4, p2)
    ops.conectProfile(p4, p3)
    ops.conectProfile(p4, p5)
    ops.conectProfile(p5, p3)

    Assert.assertEquals(ops.friendSuggestion(p1), "{\"profile_id\":1,\"profile_name\":\"1\",\"suggested_friends\":[{\"profile_name\":\"4\",\"mutual_friends\":2},{\"profile_name\":\"5\",\"mutual_friends\":1}]}")
    Assert.assertEquals(ops.friendSuggestion(p2), "{\"profile_id\":2,\"profile_name\":\"2\",\"suggested_friends\":[{\"profile_name\":\"3\",\"mutual_friends\":2},{\"profile_name\":\"5\",\"mutual_friends\":1}]}")
    Assert.assertEquals(ops.friendSuggestion(p3), "{\"profile_id\":3,\"profile_name\":\"3\",\"suggested_friends\":[{\"profile_name\":\"2\",\"mutual_friends\":2}]}")
    Assert.assertEquals(ops.friendSuggestion(p4), "{\"profile_id\":4,\"profile_name\":\"4\",\"suggested_friends\":[{\"profile_name\":\"1\",\"mutual_friends\":2}]}")
    Assert.assertEquals(ops.friendSuggestion(p5), "{\"profile_id\":5,\"profile_name\":\"5\",\"suggested_friends\":[{\"profile_name\":\"1\",\"mutual_friends\":1},{\"profile_name\":\"2\",\"mutual_friends\":1}]}")
  }

}