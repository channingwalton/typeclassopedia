package org.typeclassopedia.transformers

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.typeclassopedia.Blub

class OptionTSpec extends AnyFlatSpec with Matchers {

  "OptionT" must "map" in {
    OptionT(Blub(Option("hi"))).map(_ => 1) mustEqual OptionT(Blub(Option(1)))
    OptionT(Blub(Option.empty[String])).map(_ => 1) mustEqual OptionT(Blub(Option.empty[Int]))
  }

  it must "flatMap" in {
    val fm: OptionT[Blub, Int] = OptionT(Blub(Option(1)))
    OptionT(Blub(Option("hi"))).flatMap(_ => fm) mustEqual fm
  }

  it must "do the for-comprehension thing with all the options" in {
    val r = for {
      a <- OptionT(Blub(Option(1)))
      b <- OptionT(Blub(Option("foo")))
    } yield a + b.length
    r mustEqual OptionT(Blub(Option(4)))
  }

  it must "do the for-comprehension thing with a none" in {
    val r = for {
      a <- OptionT(Blub(Option(1)))
      b <- OptionT(Blub(Option.empty[Int]))
    } yield a + b
    r mustEqual OptionT(Blub(Option.empty[Int]))
  }

  it must "provide useful Option-like methods" in {
    OptionT(Blub(Option(1))).isDefined mustBe Blub(true)
    OptionT(Blub(Option.empty[Int])).isDefined mustBe Blub(false)

    OptionT(Blub(Option(1))).getOrElse(2) mustBe Blub(1)
    OptionT(Blub(Option.empty[Int])).getOrElse(2) mustBe Blub(2)
  }
}
