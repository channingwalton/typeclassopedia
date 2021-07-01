package org.typeclassopedia

import org.scalactic.*
import org.scalactic.Arbitrary.*
import org.sc

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import org.typeclassopedia.std.Options.{given, *}
import org.typeclassopedia.std.Lists.{given, *}
import scala.language.implicitConversions

class AlternativeSpec extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks {

  "Option alternative" must "choose" in {
    val options = Table(
      ("a", "b", "result"),
      (none[Int], none[Int], none[Int]),
      (1.some, none[Int], 1.some),
      (none[Int], 2.some, 2.some),
      (1.some, 2.some, 1.some)
    )

    forAll(options)((a: Option[Int], b: Option[Int], r: Option[Int]) => a <|> b mustEqual r)
  }

  "List Alternative" must "concatenate lists" in {
    ScalaCheckPropertyChecks.forAll((a: List[Int], b: List[Int]) => a <|> b mustEqual (a ++ b))
  }
}
