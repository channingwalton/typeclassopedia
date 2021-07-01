package org.typeclassopedia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.typeclassopedia.std.Options.{given , *}
import org.typeclassopedia.std.Lists.{given , *}
import Blub.{given , *}
import scala.language.implicitConversions

class ApplicativeSpec extends AnyFlatSpec with Matchers {

  val addInts: (Int) => (Int) => (Int) => Int = ((a: Int, b: Int, c: Int) => a + b + c).curried

  def X2: (Int) => Int = (_: Int) * 2

  "An applicative" must "<*>" in { 1.some <*> (2.some <*> (3.some map addInts)) mustEqual Some(6) }
  it must "<*> with List" in { (List(1, 2) <*> List(X2)) mustEqual List(2, 4) }
  it must "<*> with two Lists" in { (List(1, 2) <*> (List(2, 3) <*> (List(3, 4) map addInts))) mustEqual List(6, 7, 7, 8, 7, 8, 8, 9) }
  it must "<*> with None" in { 1.some <*> (2.some <*> (None map addInts)) mustEqual None }
  it must "support new Applicatives" in { Blub(1) <*> Blub((_: Int) + 1) mustEqual Blub(2) }
  it must "support ⊛" in { (Blub(1) ⊛ Blub(2) ⊛ Blub(3))(_ + _ + _) mustEqual Blub(6) }
  it must "obey the law relating Applicative to Functor" in { (Blub(2) map X2) mustEqual (Blub(2) <*> Blub(X2)) }
}
