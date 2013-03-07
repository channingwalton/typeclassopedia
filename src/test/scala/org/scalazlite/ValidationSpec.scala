package org.scalazlite

import org.scalatest.FlatSpec

import ScalazLite._

class ValidationSpec extends FlatSpec {

  "Validation" should "be an applicative" in {
    val v: Validation[String, Int] = Success(1)

    (v |@| v) === Success(2)
  }

}