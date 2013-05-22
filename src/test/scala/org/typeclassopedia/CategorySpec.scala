package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class CategorySpec extends FlatSpec {

  val f = (x: Int) ⇒ (x * 7).toString
  val g = (s: String) ⇒ s.reverse.toInt

  "A category" should "compose" in {
    assert((g <<< f apply 33) === 132)
    assert((f >>> g apply 33) === 132)
  }
}