package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class ArrowChoiceSpec extends FlatSpec {

  val f = (x: Int) ⇒ (x * 7).toString
  val g = (s: String) ⇒ s.toUpperCase

  "ArrowChoice" should "left" in {
    assert((f.left[String] apply Left(1)) === Left("7"))
    assert((f.left apply Right("hi")) === Right("hi"))
  }

  it should "right" in {
    assert((g.right[Int] apply Right("hi")) === Right("HI"))
    assert((g.right apply Left(1)) === Left(1))
  }

  it should "+++" in {
    assert((f +++ g apply Left(1)) === Left("7"))
    assert((f +++ g apply Right("hi")) === Right("HI"))
  }

  it should "|||" in {
    assert((f ||| g apply Left(1)) === "7")
    assert((f ||| g apply Right("hi")) === "HI")
  }
}