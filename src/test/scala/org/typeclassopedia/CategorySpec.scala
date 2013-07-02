package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class CategorySpec extends FlatSpec with MustMatchers {

  val f = (x: Int) ⇒ (x * 7).toString
  val g = (s: String) ⇒ s.reverse.toInt

  "A category" should "compose" in {
    (g <<< f apply 33) must be === 132
    (f >>> g apply 33) must be === 132
  }
}