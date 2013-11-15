package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._
import Matchers._

private class SemigroupSpec extends FlatSpec {
  "Append" should "append ints" in { (1 |+| 2) === 3 }
  it should "append options" in {
    (1.some |+| 2.some) === 3.some
    (1.some |+| None) === 1.some
    (None |+| 1.some) === 1.some
  }
}