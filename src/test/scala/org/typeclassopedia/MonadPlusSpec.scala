package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class MonadPlusSpec extends FlatSpec {

  val x: Option[Int] = Some(1)

  "Monad Plus" should "mzero" in {
    assert(implicitly[MonadPlus[List]].mzero === Nil)
  }

  it should "support mplus for list" in assert( (List(1) mplus List(2)) === List(1,2))

  it should "support mplus for option" in {
    assert( (1.some mplus 2.some) === Some(1) )
    assert( (none[Int] mplus 2.some) === Some(2) )
    assert( (none[Int] mplus none[Int]) === None )
  }
}