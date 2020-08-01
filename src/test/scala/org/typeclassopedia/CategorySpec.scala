package org.typeclassopedia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.typeclassopedia.Typeclassopedia._

class CategorySpec extends AnyFlatSpec with Matchers {

  val f = (x: Int) => (x * 7).toString
  val g = (s: String) => s.reverse.toInt

  "A category" must "compose" in {
    (g <<< f apply 33) mustEqual 132
    (f >>> g apply 33) mustEqual 132
  }
}
