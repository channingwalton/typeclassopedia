package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class MonadPlusSpec extends FlatSpec with MustMatchers {

  "Monad Plus" should "support mzero" in {
    implicitly[MonadPlus[List]].mzero must be === Nil
  }

  it should "support mplus for list" in { (List(1) mplus List(2)) must be === List(1, 2) }

  it should "support mplus for option" in {
    (1.some mplus 2.some) must be === Some(1)
    (none[Int] mplus 2.some) must be === Some(2)
    (none[Int] mplus none[Int]) must be === None
  }

  it should "obey monad plus laws" in {
    // ListAll has the implementation of MonadPlus for lists
    val mzero = ListAll.mzero[Int]

    val add = (i: Int) ⇒ i :: i :: Nil

    (mzero >>= add) must be === mzero
    List(1) >> Nil must be === Nil
  }
}