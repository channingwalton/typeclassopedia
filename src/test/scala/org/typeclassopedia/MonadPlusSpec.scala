package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._
import Matchers._

class MonadPlusSpec extends FlatSpec with Matchers {

  "Monad Plus" should "support mzero" in {
    implicitly[MonadPlus[List]].mzero[Int] shouldEqual List.empty[Int]
  }

  it should "support mplus for list" in { (List(1) mplus List(2)) shouldEqual List(1, 2) }

  it should "support mplus for option" in {
    (1.some mplus 2.some) shouldEqual Some(1)
    (none[Int] mplus 2.some) shouldEqual Some(2)
    (none[Int] mplus none[Int]) shouldEqual None
  }

  it should "obey monad plus laws" in {
    // ListAll has the implementation of MonadPlus for lists
    val mzero = ListAll.mzero[Int]

    val add = (i: Int) ⇒ i :: i :: Nil

    (mzero >>= add) shouldEqual mzero
    List(1) >> List.empty[Int] shouldEqual List.empty[Int]
  }
}