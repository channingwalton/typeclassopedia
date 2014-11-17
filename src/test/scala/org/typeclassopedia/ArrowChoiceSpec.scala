package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class ArrowChoiceSpec extends FlatSpec with Matchers {

  val f = (x: Int) ⇒ (x * 7).toString
  val g = (s: String) ⇒ s.toUpperCase

  "ArrowChoice" should "left" in {
    (f.left[String] apply Left[Int, String](1)) shouldEqual Left[String, String]("7")
    (f.left apply Right[Int, String]("hi")) shouldEqual Right[Int, String]("hi")
  }

  it should "right" in {
    (g.right[Int] apply Right[Int, String]("hi")) shouldEqual Right[Int, String]("HI")
    (g.right apply Left[Int, String](1)) shouldEqual Left[Int, String](1)
  }

  it should "+++" in {
    (f +++ g apply Left[Int, String](1)) shouldEqual Left[String, String]("7")
    (f +++ g apply Right[Int, String]("hi")) shouldEqual Right[Int, String]("HI")
  }

  it should "|||" in {
    (f ||| g apply Left[Int, String](1)) shouldEqual "7"
    (f ||| g apply Right[Int, String]("hi")) shouldEqual "HI"
  }
}