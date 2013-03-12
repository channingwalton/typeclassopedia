package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class FoldableSpec extends FlatSpec {

  "A foldable" should "fold" in {
    val l = 1 :: 2 :: 3 :: Nil
    assert(l.foldMap(identity) === 6)
  }
}