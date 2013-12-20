package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._
import org.scalatest.prop.PropertyChecks

class AlternativeSpec extends FlatSpec with Matchers with PropertyChecks {

  "Option alternative" should "choose" in {
    val options = Table(
      ("a", "b", "result"),
      (none[Int], none[Int],  none[Int]),
      (1.some,    none[Int],  1.some),
      (none[Int], 2.some,     2.some),
      (1.some,    2.some,     1.some)
    )

    forAll(options) { (a: Option[Int], b: Option[Int], r: Option[Int]) => a <|> b shouldEqual r }
  }

  "List Alternative" should "concatenate lists" in
    forAll { (a: List[Int], b: List[Int]) ⇒ a <|> b shouldEqual (a ++ b) }
}