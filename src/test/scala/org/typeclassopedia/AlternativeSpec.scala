package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._
import org.scalatest.prop.PropertyChecks

class AlternativeSpec extends FlatSpec with Matchers with PropertyChecks {

  "Option alternative" should "return None for Nones" in { (none[Int] <|> none[Int]) shouldEqual None }
  it should "return the first of Some/None" in { (1.some <|> none) shouldEqual Some(1) }
  it should "return the first of Some/Some" in { (1.some <|> 2.some) shouldEqual Some(1) }
  it should "return the second of None/Option" in { (none[Int] <|> 2.some) shouldEqual Some(2) }

  "List Alternative" should "concatenate lists" in
    forAll { (a: List[Int], b: List[Int]) ⇒ a <|> b shouldEqual (a ++ b) }
}