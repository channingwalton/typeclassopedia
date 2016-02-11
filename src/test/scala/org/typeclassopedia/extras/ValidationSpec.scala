package org.typeclassopedia.extras

import java.lang.String
import scala.Int
import scala.Predef.implicitly
import org.scalatest._
import org.typeclassopedia.Applicative
import org.typeclassopedia.Typeclassopedia._

class ValidationSpec extends FlatSpec with Matchers {

  type StringValidation[S] = Validation[String, S]
  val imp = implicitly[Applicative[StringValidation]]
  // it lives!

  val v: StringValidation[Int] = Success[String, Int](3)
  val e: StringValidation[Int] = Failure[String, Int]("no.")
  val f: StringValidation[Int ⇒ Int] = Success[String, Int ⇒ Int](2 *)

  "Validation" should "be an applicative" in { (v <*> f) shouldEqual Success[String, Int](6) }
  it should "deal with failure" in { (e <*> f) shouldEqual Failure[String, Int]("no.") }
  it should "use an applicative builder to apply a function" in { (v ⊛ v) { _ - _ } shouldEqual Success[String, Int](0) }
  it should "use an applicative builder and accumulate failure" in { (e ⊛ e ⊛ v) { _ + _ + _ } shouldEqual Failure[String, Int]("no.no.") }
}
