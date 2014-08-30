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
}