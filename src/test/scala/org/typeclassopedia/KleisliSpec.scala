package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class KleisliSpec extends FlatSpec {

  val sqrt = (n: Double) ⇒ if (n >= 0) Some(math.sqrt(n)) else None

  "Kleisli" should "compose functions" in {
    val root4 = sqrt >=> sqrt
    assert(root4(16) === Some(2))
  }

  "Kleisli syntax" should "offer first" in {
    assert(kleisli(sqrt).first[String]((4, "hi")) === Some((2.0, "hi")))
    assert(kleisli(sqrt).first[String]((-1, "hi")) === None)
  }
}