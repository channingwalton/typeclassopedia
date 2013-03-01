package org.scalazlite

import org.scalatest._
import ScalazLite._

class ApplicativeSpec extends FlatSpec {

  "An applicative" should "<*>" in {
    val x: Option[Int] = Some(1)
    val y: Option[Int] = Some(2)
    val z: Option[Int] = Some(3)

    val addInts = ((a: Int, b: Int, c: Int) ⇒ a + b + c).curried

    x <*> (y <*> (z map addInts)) === Some(6)
  }

  it should "support new Applicatives" in {
    Blub(1) <*> Blub((_: Int) + 1) === Blub(2)
  }

  it should "support |@|" in {
    (Blub(1) |@| Blub(2))(_ + _) === Blub(3)
  }
}