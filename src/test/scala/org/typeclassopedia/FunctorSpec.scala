package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class FunctorSpec extends FlatSpec {

  "A functor" should "map stuff" in assert(Blub(1).map(2*) === Blub(2))
}