package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class MonadPlusSpec extends FlatSpec {

  val x: Option[Int] = Some(1)

  "Monad Plus" should "support mzero" in {
    assert(implicitly[MonadPlus[List]].mzero === Nil)
  }

  it should "support mplus for list" in assert( (List(1) mplus List(2)) === List(1,2))

  it should "support mplus for option" in {
    assert( (1.some mplus 2.some) === Some(1) )
    assert( (none[Int] mplus 2.some) === Some(2) )
    assert( (none[Int] mplus none[Int]) === None )
  }

  it should "obey monad plus laws" in {
    // ListAll has the implementation of MonadPlus for lists
    val mzero = ListAll.mzero[Int]

    val add = (i:Int) => i :: i :: Nil

    assert((mzero >>= add) === mzero)
    assert( (List(1) >> Nil) === Nil)
  }

}