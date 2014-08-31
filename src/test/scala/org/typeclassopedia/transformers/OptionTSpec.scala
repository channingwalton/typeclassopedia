package org.typeclassopedia.transformers

import org.scalatest.{Matchers, FlatSpec}
import org.typeclassopedia.Blub

class OptionTSpec extends FlatSpec with Matchers {

  "OptionT" should "map" in {
    OptionT(Blub(Option("hi"))).map(_ ⇒ 1) shouldEqual OptionT(Blub(Option(1)))
    OptionT(Blub(Option.empty[String])).map(_ ⇒ 1) shouldEqual OptionT(Blub(Option.empty[Int]))
  }

  it should "flatMap" in {
    val fm: OptionT[Blub, Int] = OptionT(Blub(Option(1)))
    OptionT(Blub(Option("hi"))).flatMap(_ ⇒ fm) shouldEqual fm
  }

  it should "do the for-comprehension thing with all the options" in {
    val r = for {
      a ← OptionT(Blub(Option(1)))
      b ← OptionT(Blub(Option(1)))
    } yield a + b
    r shouldEqual OptionT(Blub(Option(2)))
  }

  it should "do the for-comprehension thing with a none" in {
    val r = for {
      a ← OptionT(Blub(Option(1)))
      b ← OptionT(Blub(Option.empty[Int]))
    } yield a + b
    r shouldEqual OptionT(Blub(Option.empty[Int]))
  }

  it should "provide useful Option-like methods" in {
    OptionT(Blub(Option(1))).isDefined shouldBe Blub(true)
    OptionT(Blub(Option.empty[Int])).isDefined shouldBe Blub(false)

    OptionT(Blub(Option(1))).isEmpty shouldBe Blub(false)
    OptionT(Blub(Option.empty[Int])).isEmpty shouldBe Blub(true)

    OptionT(Blub(Option(1))).getOrElse(2) shouldBe Blub(1)
    OptionT(Blub(Option.empty[Int])).getOrElse(2) shouldBe Blub(2)
  }
}