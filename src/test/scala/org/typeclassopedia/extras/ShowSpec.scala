package org.typeclassopedia.extras

import org.scalatest._
import org.typeclassopedia.Typeclassopedia._

import org.typeclassopedia.Blub

class ShowSpec extends FlatSpec with Matchers {

  "Show" should "produce a nice string for a string value" in {
    Blub("hi").show shouldEqual "A blub of hi"
  }
}