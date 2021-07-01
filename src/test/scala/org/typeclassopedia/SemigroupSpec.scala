package org.typeclassopedia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.typeclassopedia.std.Monoids.{given, *}
import org.typeclassopedia.std.Options.{given, *}
import scala.language.implicitConversions

private class SemigroupSpec extends AnyFlatSpec with Matchers {
  "Append" must "append ints" in { (1 |+| 2) mustEqual 3 }
  it must "append options" in {
    (1.some |+| 2.some) mustEqual 3.some
    (1.some |+| None) mustEqual 1.some
    (none[Int] |+| 1.some) mustEqual 1.some
  }
}
