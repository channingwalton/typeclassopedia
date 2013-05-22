package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class KleisliSpec extends FlatSpec {

  val sqrt = (n: Double) ⇒ if (n >= 0) Some(math.sqrt(n)) else None
  val times2 = (n: Double) => (2 * n).some
  val double = (s: String) => (s + s).some

  "Kleisli" should "compose functions" in {
    val root4 = sqrt >=> sqrt
    assert(root4(16) === Some(2))
  }

  "Kleisli syntax" should "offer first" in {
    assert(kleisli(sqrt).first[String]((4, "hi")) === Some((2.0, "hi")))
    assert(kleisli(sqrt).first[String]((-1, "hi")) === None)
  }

  it should "offer second" in {
    assert(kleisli(sqrt).second[String](("hi", 4)) === Some(("hi", 2.0)))
    assert(kleisli(sqrt).second[String](("hi", -1)) === None)
  }

  it should "offer ***" in {
    assert((kleisli(sqrt) *** kleisli(double))(4, "hi") === Some((2.0, "hihi")))
    assert((kleisli(sqrt) *** kleisli(double))(-1, "hi") === None)
  }

  it should "offer &&&" in {
    assert((kleisli(sqrt) &&& kleisli(times2))(4) === Some((2.0, 8)))
    assert((kleisli(sqrt) &&& kleisli(times2))(-1) === None)
  }
}