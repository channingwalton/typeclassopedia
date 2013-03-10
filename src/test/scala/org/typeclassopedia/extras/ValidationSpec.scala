package org.typeclassopedia.extras

import org.scalatest.FlatSpec
import org.typeclassopedia.Typeclassopedia._
import org.typeclassopedia.Applicative

class ValidationSpec extends FlatSpec {

  type StringValidation[S] = Validation[String, S]
  implicitly[Applicative[StringValidation]] // it lives!

  val v: StringValidation[Int] = Success(3)
  val e: StringValidation[Int] = Failure("no.")
  val f: StringValidation[Int ⇒ Int] = Success(2*)

  "Validation" should "be an applicative" in assert((v <*> f) === Success(6))
  it should "deal with failure" in assert((e <*> f) === Failure("no."))
  it should "use an applicative builder to apply a function" in assert(((v |@| v) { _ - _ }) === Success(0))
  it should "use an applicative builder and accumulate failure" in assert(((e |@| e |@| v) { _ + _ + _ }) === Failure("no.no."))
}