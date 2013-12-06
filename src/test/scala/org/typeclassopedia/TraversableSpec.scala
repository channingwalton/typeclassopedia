package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._
import Matchers._

class TraversableSpec extends FlatSpec {

  "A Traversable" should "support traverse" in { 1.some.traverse(v ⇒ List(v, v + 1)) shouldEqual List(1.some, 2.some) }

  it should "support sequenceA" in { List(1.some).sequenceA shouldEqual List(1).some }

  it should "support sequence" in { List(1.some).sequence shouldEqual List(1).some }

  it should "support mapM" in { 1.some.mapM(v ⇒ List(v)) shouldEqual List(1.some) }

  it should "preserve order" in { List(1, 2, 3).traverse(Option(_)) shouldEqual Some(List(1, 2, 3)) }

  it should "traverse through option effect" in {
    val fn = (x: Int) ⇒ if (x < 3) Some(x) else None
    List(1, 2, 3).traverse(fn) shouldEqual none[List[Int]]
  }

  it should "not blow the stack" in {
    val s = List.range(0, 32 * 1024).traverse(x ⇒ x.some)
    s.map(_.take(3)) shouldEqual Some(List(0, 1, 2))
  }
}