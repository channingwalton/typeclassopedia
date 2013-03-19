package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class FoldableSpec extends FlatSpec {

  "A foldable" should "foldMap" in assert(List(1, 2, 3).foldMap(identity) === 6)
  it should "fold applying a function and monoid" in assert(List(1, 2, 3).foldMap(_ ⇒ 1) === 3)
}