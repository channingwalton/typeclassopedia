package org.typeclassopedia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.typeclassopedia.std.Options._

import scala.language.implicitConversions

class MonadSpec extends AnyFlatSpec with Matchers {

  val x: Option[Int] = Some(1)

  "A monad" must "flatMap that thang" in {
    // I'll use >>= instead so we know Typeclassopedia is being used
    (x >>= (x => Some(x + 1))) mustEqual Some(2)
  }

  it must "support new monads" in { (Blub(1) >>= (x => Blub(x + 1))) mustEqual Blub(2) }
}
