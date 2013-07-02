package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class ArrowSpec extends FlatSpec with MustMatchers {

  val f = (x: Int) ⇒ (x * 7).toString
  val g = (s: String) ⇒ s.toUpperCase
  val h = (x: Int) ⇒ x / 2

  "Arrow" should "first" in {
    (f.first((3, 1)) must be === ("21", 1))
  }

  it should "second" in {
    (f.second((1, 3)) must be === (1, "21"))
  }

  it should "***" in {
    ((f *** g)((2, "hello")) must be === ("14", "HELLO"))
  }

  it should "&&&" in {
    ((f &&& h)(2) must be === ("14", 1))
  }

  it should "obey the identity law" in {
    val id = (a: Int) ⇒ a
    (Function1Arrows.arr(id) must be === id)
  }
}