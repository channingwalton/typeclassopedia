package org.typeclassopedia.transformers

import java.lang.String
import scala.{Int, List}
import org.scalatest.{Matchers, FlatSpec}
import org.typeclassopedia.Blub

class ListTTest extends FlatSpec with Matchers {
  "ListT" should "map" in {
    ListT(Blub(List("hi", "bye"))).map(_ ⇒ 1) shouldEqual ListT(Blub(List(1, 1)))
    ListT(Blub(List.empty[String])).map(_ ⇒ 1) shouldEqual ListT(Blub(List.empty[Int]))
  }

  it should "flatMap" in {
    val fm: ListT[Blub, Int] = ListT(Blub(List(1, 2)))
    ListT(Blub(List("hi", "bye"))).flatMap(_ ⇒ fm) shouldEqual ListT(Blub(List(1, 2, 1, 2)))
  }

  it should "do the for-comprehension thing with all the lists" in {
    val r = for {
      a ← ListT(Blub(List(1, 2)))
      b ← ListT(Blub(List(3, 4)))
    } yield a + b
    r shouldEqual ListT(Blub(List(4, 5, 5, 6)))
  }

  it should "do the for-comprehension thing with a nil" in {
    val r = for {
      a ← ListT(Blub(List(1)))
      b ← ListT(Blub(List.empty[Int]))
    } yield a + b
    r shouldEqual ListT(Blub(List.empty[Int]))
  }
}
