package org.typeclassopedia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.typeclassopedia.Typeclassopedia._

class KleisliSpec extends AnyFlatSpec with Matchers {

  val sqrt   = (n: Double) => if (n >= 0) Some(math.sqrt(n)) else None
  val times2 = (n: Double) => (2 * n).some
  val double = (s: String) => (s + s).some

  "Kleisli" must "compose functions" in {
    val root4 = sqrt >=> sqrt
    root4(16) mustEqual Some(2)
  }

  "Kleisli syntax" must "offer first" in {
    kleisli(sqrt).first[String]((4, "hi")) mustEqual Some((2.0, "hi"))
    kleisli(sqrt).first[String]((-1, "hi")) mustEqual None
  }

  it must "offer second" in {
    kleisli(sqrt).second[String](("hi", 4)) mustEqual Some(("hi", 2.0))
    kleisli(sqrt).second[String](("hi", -1)) mustEqual None
  }

  it must "offer ***" in {
    (kleisli(sqrt) *** kleisli(double))((4, "hi")) mustEqual Some((2.0, "hihi"))
    (kleisli(sqrt) *** kleisli(double))((-1, "hi")) mustEqual None
  }

  it must "offer &&&" in {
    (kleisli(sqrt) &&& kleisli(times2))(4) mustEqual Some((2.0, 8))
    (kleisli(sqrt) &&& kleisli(times2))(-1) mustEqual None
  }
}
