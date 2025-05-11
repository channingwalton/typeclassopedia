package org.typeclassopedia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import scala.language.implicitConversions
import org.typeclassopedia.std.Options.{given, _}
import org.typeclassopedia.std.Lists.{given, _}

class ComonadSpec extends AnyFlatSpec with Matchers {

  "A comonad" must "duplicate Option" in { Option(1).duplicate mustEqual Option(Option(1)) }
  it must "duplicate List" in { List(1).duplicate mustEqual List(List(1)) }
  it must "duplicate Blub" in { Blub(1).duplicate mustEqual Blub(Blub(1)) }

  it must "extend Option" in { Option(1).extend(_ => 2) mustEqual Option(2) }
  it must "extend List" in { List(1).extend(_.headOption.map(_ + 1).getOrElse(-1)) mustEqual List(2) }
  it must "extend Blub" in { Blub(1).extend(_.v + 1) mustEqual Blub(2) }
}
