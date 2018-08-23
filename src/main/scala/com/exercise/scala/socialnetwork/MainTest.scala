package com.exercise.scala.socialnetwork

import com.exercise.scala.socialnetwork.model.Profile

import scala.collection.SortedSet

object MainTest extends App {

  val p1 = new Profile()
  p1.name_=("Vinicius")
  p1.id = 1
  val p2 = new Profile()
  p2.name_=("Armando")
  p2.id = 2

  implicit val myOrdering = new Ordering[Profile] {
    def compare(x: Profile, y: Profile): Int = {
      if (x.id < y.id) -1
      else if (x.id > y.id) 1
      else 0
    }
  }

  var numbers = SortedSet.empty(myOrdering)

  numbers += p1
  numbers += p2
  numbers += p1

  println(numbers.size)
  numbers.toList.foreach(p => println(p.name))
}
