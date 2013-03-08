package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class ApplicativeSpec extends FlatSpec {

  "An applicative" should "<*>" in {
    val addInts = ((a: Int, b: Int, c: Int) ⇒ a + b + c).curried

    assert(1.some <*> (2.some <*> (3.some map addInts)) === Some(6))
    assert(1.some <*> (2.some <*> (None map addInts)) === None)
  }

  it should "support new Applicatives" in {
    assert(Blub(1) <*> Blub((_: Int) + 1) === Blub(2))
  }

  it should "support |@|" in {
    assert((Blub(1) |@| Blub(2))(_ + _) === Blub(3))
    assert((Blub(1) |@| Blub(2) |@| Blub(3))(_ + _ + _) === Blub(6))
  }
}