package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class MonadSpec extends FlatSpec {

  val x: Option[Int] = Some(1)

  "A monad" should "flatMap that thang" in {
    // I'll use >>= instead so we know Typeclassopedia is being used
    assert((x >>= (x ⇒ Some(x + 1))) === Some(2))
  }

  it should "support new monads" in assert((Blub(1) >>= (x ⇒ Blub(x + 1))) === Blub(2))
}