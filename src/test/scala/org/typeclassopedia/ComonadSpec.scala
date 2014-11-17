package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class ComonadSpec extends FlatSpec with Matchers {

  "A comonad" should "duplicate Option" in { Option(1).duplicate shouldEqual Option(Option(1)) }
  it should "duplicate List" in { List(1).duplicate shouldEqual List(List(1)) }
  it should "duplicate Blub" in { Blub(1).duplicate shouldEqual Blub(Blub(1)) }

  it should "extend Option" in { Option(1).extend(_ ⇒ 2) shouldEqual Option(2) }
  it should "extend List" in { List(1).extend(_.headOption.map(_ + 1).getOrElse(-1)) shouldEqual List(2) }
  it should "extend Blub" in { Blub(1).extend(_.v + 1) shouldEqual Blub(2) }
}
