package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class SemigroupSpec extends FlatSpec {

  "Append" should "append ints" in assert((1 |+| 2) === 3)

  it should "append options" in {
    assert((1.some |+| 2.some) === 3.some)
    assert((1.some |+| None) === 1.some)
    assert((None |+| 1.some) === 1.some)
  }
}