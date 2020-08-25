package org.typeclassopedia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import scala.language.implicitConversions

class FunctorSpec extends AnyFlatSpec with Matchers {

  val inc: Int => Int    = 1 + _
  val double: Int => Int = 2 * _

  "A functor" must "map stuff" in { Blub(1).map(double) mustEqual Blub(2) }
  it must "obey the identity law" in { Blub(1).map(identity) mustEqual Blub(1) }
  it must "obey the composition law" in { Blub(1).map(inc andThen double) mustEqual Blub(1).map(inc).map(double) }
  it must "obey the functor laws" in {}
}
