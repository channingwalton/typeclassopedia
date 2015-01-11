package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class ApplicativeSpec extends FlatSpec with Matchers {

  val addInts: (Int) ⇒ (Int) ⇒ (Int) ⇒ Int = ((a: Int, b: Int, c: Int) ⇒ a + b + c).curried

  def X2: (Int) ⇒ Int = (_: Int) * 2

  "An applicative" should "<*>" in { 1.some <*> (2.some <*> (3.some map addInts)) shouldEqual Some(6) }
  it should "<*> with List" in { (List(1,2) <*> List(X2)) shouldEqual List(2, 4) }
  it should "<*> with two Lists" in { (List(1,2) <*> (List(2, 3) <*> (List(3, 4) map addInts))) shouldEqual List(6, 7, 7, 8, 7, 8, 8, 9) }
  it should "<*> with None" in { 1.some <*> (2.some <*> (None map addInts)) shouldEqual None }
  it should "support new Applicatives" in { Blub(1) <*> Blub((_: Int) + 1) shouldEqual Blub(2) }
  it should "support ⊛" in { (Blub(1) ⊛ Blub(2) ⊛ Blub(3))(_ + _ + _) shouldEqual Blub(6) }
  it should "obey the law relating Applicative to Functor" in { (Blub(2) map X2) shouldEqual (Blub(2) <*> Blub(X2)) }
}