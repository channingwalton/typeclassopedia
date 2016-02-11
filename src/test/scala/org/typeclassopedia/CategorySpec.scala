package org.typeclassopedia

import scala.Int
import scala.Predef.wrapString

import java.lang.String
import Typeclassopedia._
import org.scalatest._

class CategorySpec extends FlatSpec with Matchers {

  val f = (x: Int) ⇒ (x * 7).toString
  val g = (s: String) ⇒ s.reverse.toInt

  "A category" should "compose" in {
    (g <<< f apply 33) shouldEqual 132
    (f >>> g apply 33) shouldEqual 132
  }
}
