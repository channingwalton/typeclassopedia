package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class ArrowSpec extends FlatSpec {

  val f = (x: Int) => (x * 7).toString
  val g = (s: String) => s.toUpperCase

  "Arrow" should "first" in {
    assert(f.first((3, 1)) ===("21", 1))
  }

  it should "second" in {
    assert(f.second((1, 3)) ===(1, "21"))
  }

  it should "*** (compose)" in {
    assert((f *** g)((2, "hello")) ===("14", "HELLO"))
  }

}
