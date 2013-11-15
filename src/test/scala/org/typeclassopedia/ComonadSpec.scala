package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class ComonadSpec extends FlatSpec with MustMatchers {

  "A comonad" should "duplicate Option" in { Option(1).duplicate === Option(Option(1)) }
  it should "duplicate List" in { List(1).duplicate === List(List(1)) }
  it should "duplicate Blub" in { Blub(1).duplicate === Blub(Blub(1)) }

  it should "extend Option" in { Option(1).extend(_ ⇒ 2) === Option(2) }
  it should "extend List" in { List(1).extend(_.head + 1) === List(2) }
  it should "extend Blub" in { Blub(1).extend(_.v + 1) === Blub(2) }
}
