package org.typeclassopedia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class ArrowChoiceSpec extends AnyFlatSpec with Matchers {

  val f = (x: Int) => (x * 7).toString
  val g = (s: String) => s.toUpperCase

  "ArrowChoice" must "left" in {
    (f.left[String] apply Left[Int, String](1)) mustEqual Left[String, String]("7")
    (f.left apply Right[Int, String]("hi")) mustEqual Right[Int, String]("hi")
  }

  it must "right" in {
    (g.right[Int] apply Right[Int, String]("hi")) mustEqual Right[Int, String]("HI")
    (g.right apply Left[Int, String](1)) mustEqual Left[Int, String](1)
  }

  it must "+++" in {
    (f +++ g apply Left[Int, String](1)) mustEqual Left[String, String]("7")
    (f +++ g apply Right[Int, String]("hi")) mustEqual Right[Int, String]("HI")
  }

  it must "|||" in {
    (f ||| g apply Left[Int, String](1)) mustEqual "7"
    (f ||| g apply Right[Int, String]("hi")) mustEqual "HI"
  }
}
