package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class ApplicativeSpec extends FlatSpec with MustMatchers {

  val addInts = ((a: Int, b: Int, c: Int) ⇒ a + b + c).curried

  def X2 = (_: Int) * 2

  "An applicative" should "<*>" in { 1.some <*> (2.some <*> (3.some map addInts)) === Some(6) }
  it should "<*> with None" in { 1.some <*> (2.some <*> (None map addInts)) === None }
  it should "support new Applicatives" in { Blub(1) <*> Blub((_: Int) + 1) === Blub(2) }
  it should "support ⊛" in { (Blub(1) ⊛ Blub(2) ⊛ Blub(3))(_ + _ + _) === Blub(6) }
  it should "obey the law relating Applicative to Functor" in { (Blub(2) map (X2)) === (Blub(2) <*> Blub(X2)) }
}