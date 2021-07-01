package org.typeclassopedia.extras

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.typeclassopedia.Applicative
import org.typeclassopedia.extras.Validations.{given, *}
import org.typeclassopedia.std.Monoids.monoidString
import scala.language.implicitConversions

class ValidationSpec extends AnyFlatSpec with Matchers {

  type StringValidation[S] = Validation[String, S]

  val v: StringValidation[Int]        = Success[String, Int](3)
  val e: StringValidation[Int]        = Failure[String, Int]("no.")
  val f: StringValidation[Int => Int] = Success[String, Int => Int](2 * _)

  "Validation" must "be an applicative" in { (v <*> f) mustEqual Success[String, Int](6) }
  it must "deal with failure" in { (e <*> f) mustEqual Failure[String, Int]("no.") }
  it must "use an applicative builder to apply a function" in { (v ⊛ v)(_ - _) mustEqual Success[String, Int](0) }
  it must "use an applicative builder and accumulate failure" in { (e ⊛ e ⊛ v)(_ + _ + _) mustEqual Failure[String, Int]("no.no.") }
}
