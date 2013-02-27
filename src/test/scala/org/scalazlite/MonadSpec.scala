package org.scalazlite

import org.scalatest._
import ScalazLite._

class MonadSpec extends FlatSpec {

  "A monad" should "flatMap that thang" in {
    // I'll use >>= instead so we know scalaz-lite is being used
    val x: Option[Int] = Some(1)
    val y = x >>= (x ⇒ Some(x + 1))
    y === Some(2)
  }

}