package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class ArrowSpec extends FlatSpec {

  val f = (x: Int) ⇒ (x * 7).toString
  val g = (s: String) ⇒ s.toUpperCase
  val h = (x: Int) ⇒ x / 2

  "Arrow" should "first" in {
    assert(f.first((3, 1)) ===("21", 1))
  }

  it should "second" in {
    assert(f.second((1, 3)) ===(1, "21"))
  }

  it should "***" in {
    assert((f *** g)((2, "hello")) ===("14", "HELLO"))
  }

  it should "&&&" in {
    assert((f &&& h)(2) ===("14", 1))
  }

  it should "obey the identity law" in {
    val id = (a: Int) ⇒ a

    assert(Function1Arrows.arr(id) === id)
  }

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