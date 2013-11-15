package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class ArrowChoiceSpec extends FlatSpec with MustMatchers {

  val f = (x: Int) ⇒ (x * 7).toString
  val g = (s: String) ⇒ s.toUpperCase

  "ArrowChoice" should "left" in {
    (f.left[String] apply Left(1)) === Left("7")
    (f.left apply Right("hi")) === Right("hi")
  }

  it should "right" in {
    (g.right[Int] apply Right("hi")) === Right("HI")
    (g.right apply Left(1)) === Left(1)
  }

  it should "+++" in {
    (f +++ g apply Left(1)) === Left("7")
    (f +++ g apply Right("hi")) === Right("HI")
  }

  it should "|||" in {
    (f ||| g apply Left(1)) === "7"
    (f ||| g apply Right("hi")) === "HI"
  }
}