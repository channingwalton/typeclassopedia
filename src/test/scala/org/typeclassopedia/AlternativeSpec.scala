package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class AlternativeSpec extends FlatSpec with Matchers {

  "Option alternative" should "return None for Nones" in { (None <|> None) shouldEqual None  }
  it should "return the first if Some/None" in { (1.some <|> none) shouldEqual Some(1) }
  it should "return the first if Some/Some" in { (1.some <|> 2.some) shouldEqual Some(1) }
  it should "return the second if None/Option" in { (none[Int] <|> 2.some) shouldEqual Some(2) }
}
