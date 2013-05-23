package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class ApplicativeSpec extends FlatSpec {

  val addInts = ((a: Int, b: Int, c: Int) ⇒ a + b + c).curried

  def X2 = (_: Int) * 2

  "An applicative" should "<*>" in assert(1.some <*> (2.some <*> (3.some map addInts)) === Some(6))
  it should "<*> with None" in assert(1.some <*> (2.some <*> (None map addInts)) === None)
  it should "support new Applicatives" in assert(Blub(1) <*> Blub((_: Int) + 1) === Blub(2))
  it should "support ⊛" in assert((Blub(1) ⊛ Blub(2) ⊛ Blub(3))(_ + _ + _) === Blub(6))
  it should "obey the law relating Applicative to Functor" in assert((Blub(2) map (X2)) === (Blub(2) <*> Blub(X2)))
}