package org.typeclassopedia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class MonadPlusSpec extends AnyFlatSpec with Matchers {

  "Monad Plus" must "support mzero" in {
    implicitly[MonadPlus[List]].mzero[Int] mustEqual List.empty[Int]
  }

  it must "support mplus for list" in { (List(1) mplus List(2)) mustEqual List(1, 2) }

  it must "support mplus for option" in {
    (1.some mplus 2.some) mustEqual Some(1)
    (none[Int] mplus 2.some) mustEqual Some(2)
    (none[Int] mplus none[Int]) mustEqual None
  }

  it must "obey monad plus laws" in {
    // ListAll has the implementation of MonadPlus for lists
    val mzero = ListAll.mzero[Int]

    val add = (i: Int) => i :: i :: Nil

    (mzero >>= add) mustEqual mzero
  }
}
