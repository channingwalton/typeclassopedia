package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class CategorySpec extends FlatSpec {

  val f = (x: Int) => (x * 7).toString
  val g = (s: String) => s.reverse.toInt

  "A category" should "compose" in {
    // use the <<< method to avoid confusion with Function1.compose
    assert((g <<< f apply 33) === 132)
  }
}