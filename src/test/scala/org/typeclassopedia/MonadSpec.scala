package org.typeclassopedia

import scala.{Int, Option, Some}
import Typeclassopedia._
import org.scalatest._

class MonadSpec extends FlatSpec with Matchers {

  val x: Option[Int] = Some(1)

  "A monad" should "flatMap that thang" in {
    // I'll use >>= instead so we know Typeclassopedia is being used
    (x >>= (x ⇒ Some(x + 1))) shouldEqual Some(2)
  }

  it should "support new monads" in { (Blub(1) >>= (x ⇒ Blub(x + 1))) shouldEqual Blub(2) }
}
