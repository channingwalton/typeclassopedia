package org.typeclassopedia.extras

import org.scalatest._
import Matchers._
import org.typeclassopedia.Applicative
import org.typeclassopedia.Typeclassopedia._

class ValidationSpec extends FlatSpec {

  type StringValidation[S] = Validation[String, S]
  implicitly[Applicative[StringValidation]]
  // it lives!

  val v: StringValidation[Int] = Success(3)
  val e: StringValidation[Int] = Failure("no.")
  val f: StringValidation[Int ⇒ Int] = Success(2 *)

  "Validation" should "be an applicative" in { (v <*> f) === Success(6) }
  it should "deal with failure" in { (e <*> f) === Failure("no.") }
  it should "use an applicative builder to apply a function" in { ((v ⊛ v) { _ - _ }) === Success(0) }
  it should "use an applicative builder and accumulate failure" in { ((e ⊛ e ⊛ v) { _ + _ + _ }) === Failure("no.no.") }
}