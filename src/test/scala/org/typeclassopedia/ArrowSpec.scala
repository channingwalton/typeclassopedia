package org.typeclassopedia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.typeclassopedia.Typeclassopedia._

class ArrowSpec extends AnyFlatSpec with Matchers {

  val f = (x: Int) => (x * 7).toString
  val g = (s: String) => s.toUpperCase
  val h = (x: Int) => x / 2

  "Arrow" must "first" in {
    f.first((3, 1)) mustEqual (("21", 1))
  }

  it must "second" in {
    f.second((1, 3)) mustEqual ((1, "21"))
  }

  it must "***" in {
    (f *** g)((2, "hello")) mustEqual (("14", "HELLO"))
  }

  it must "&&&" in {
    (f &&& h)(2) mustEqual (("14", 1))
  }

  it must "obey the identity law" in {
    val id = (a: Int) => a
    Function1Arrows.arr(id) mustEqual id
  }
}
