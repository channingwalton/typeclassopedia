package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class SemigroupSpec extends FlatSpec {

  "Append" should "append ints" in {
    (1 |+| 2) === 3
  }

  it should "append options" in {
    (1.some |+| 2.some) === 3.some
    (1.some |+| None) === None
    (None |+| 1.some) === None
  }
}