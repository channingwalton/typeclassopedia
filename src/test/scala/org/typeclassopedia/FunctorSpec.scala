package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class FunctorSpec extends FlatSpec {

  val inc: Int ⇒ Int = 1 + _
  val double: Int ⇒ Int = 2 * _

  "A functor" should "map stuff" in assert(Blub(1).map(double) === Blub(2))
  it should "obey the identity law" in assert(Blub(1).map(identity) === Blub(1))
  it should "obey the composition law" in assert(Blub(1).map(inc andThen double) === Blub(1).map(inc).map(double))
}