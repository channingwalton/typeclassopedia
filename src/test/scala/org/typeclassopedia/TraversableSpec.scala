package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class TraversableSpec extends FlatSpec {

  "A Traversable" should "support sequence" in {
    assert(1.some.traverse(v ⇒ List(v, v)) === List(1.some, 1.some))
  }
}