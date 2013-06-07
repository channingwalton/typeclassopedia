package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class MonadPlusSpec extends FlatSpec {

  val x: Option[Int] = Some(1)

  "Monad Plus" should "mzero" in {
    assert(implicitly[MonadPlus[List]].mzero === Nil)
  }

  it should "support mplus" in assert( (List(1) mplus List(2)) === List(1,2))
}