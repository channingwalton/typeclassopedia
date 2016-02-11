package org.typeclassopedia

import java.lang.String
import scala.{math, Double, None, Some}

import Typeclassopedia._
import org.scalatest._

class KleisliSpec extends FlatSpec with Matchers {

  val sqrt = (n: Double) ⇒ if (n >= 0) Some(math.sqrt(n)) else None
  val times2 = (n: Double) ⇒ (2 * n).some
  val double = (s: String) ⇒ (s + s).some

  "Kleisli" should "compose functions" in {
    val root4 = sqrt >=> sqrt
    root4(16) shouldEqual Some(2)
  }

  "Kleisli syntax" should "offer first" in {
    kleisli(sqrt).first[String]((4, "hi")) shouldEqual Some((2.0, "hi"))
    kleisli(sqrt).first[String]((-1, "hi")) shouldEqual None
  }

  it should "offer second" in {
    kleisli(sqrt).second[String](("hi", 4)) shouldEqual Some(("hi", 2.0))
    kleisli(sqrt).second[String](("hi", -1)) shouldEqual None
  }

  it should "offer ***" in {
    (kleisli(sqrt) *** kleisli(double)) ((4, "hi")) shouldEqual Some((2.0, "hihi"))
    (kleisli(sqrt) *** kleisli(double)) ((-1, "hi")) shouldEqual None
  }

  it should "offer &&&" in {
    (kleisli(sqrt) &&& kleisli(times2))(4) shouldEqual Some((2.0, 8))
    (kleisli(sqrt) &&& kleisli(times2))(-1) shouldEqual None
  }
}
