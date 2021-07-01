package org.typeclassopedia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import scala.language.implicitConversions
import org.typeclassopedia.std.Monoids.{given, *}
import org.typeclassopedia.std.Options.{given, *}
import org.typeclassopedia.std.Lists.{given, *}

class TraversableSpec extends AnyFlatSpec with Matchers {

  "A Traversable" must "support traverse" in { 1.some.traverse(v => List(v, v + 1)) mustEqual List(1.some, 2.some) }

  it must "support sequenceA" in { List(1.some).sequenceA mustEqual List(1).some }

  it must "support sequence" in { List(1.some).sequence mustEqual List(1).some }

  it must "support mapM" in { 1.some.mapM(v => List(v)) mustEqual List(1.some) }

  it must "preserve order" in { List(1, 2, 3).traverse(Option(_)) mustEqual Some(List(1, 2, 3)) }

  it must "traverse through option effect" in {
    val fn = (x: Int) => if (x < 3) Some(x) else None
    List(1, 2, 3).traverse(fn) mustEqual none[List[Int]]
  }

  it must "not blow the stack" in {
    val s = List.range(0, 32 * 1024).traverse(x => x.some)
    s.map(_.take(3)) mustEqual Some(List(0, 1, 2))
  }
}
