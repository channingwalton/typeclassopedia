package org.scalazlite

import org.scalatest.FlatSpec

import ScalazLite._

class ValidationSpec extends FlatSpec with Validations {

  "Validation" should "be an applicative" in {
    type StringValidation[S] = Validation[String, S]
    implicitly[Applicative[StringValidation]] // it lives!

    val v: StringValidation[Int] = Success(3)
    val e: StringValidation[Int] = Failure("no.")
    val f: StringValidation[Int ⇒ Int] = Success(2*)

    (v <*> f) === Success(6)
    (e <*> f) === Failure("no.")

    ((v |@| v) { _ - _ }) === Success(0)
    ((e |@| e |@| v) { _ + _ + _ }) === Failure("no.no.")
  }

}