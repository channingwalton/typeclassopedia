package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class FunctorSpec extends FlatSpec {

  val plus1 = (_: Int) + 1
  val x2 = (_: Int) * 2

  "A functor" should "map stuff" in assert(Blub(1).map(x2) === Blub(2))
  it should "obey the identity law" in assert(Blub(1).map(identity) === Blub(1))
  it should "obey the composition law" in assert(Blub(1).map(x2 compose plus1) === Blub(1).map(plus1).map(x2))
}