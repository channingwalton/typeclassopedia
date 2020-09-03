package org.typeclassopedia.transformers

import java.lang.String

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import scala.{ Int, List }
import org.typeclassopedia.Blub

import scala.language.implicitConversions

class ListTTest extends AnyFlatSpec with Matchers {
  "ListT" must "map" in {
    ListT(Blub(List("hi", "bye"))).map(_ => 1) mustEqual ListT(Blub(List(1, 1)))
    ListT(Blub(List.empty[String])).map(_ => 1) mustEqual ListT(Blub(List.empty[Int]))
  }

  it must "flatMap" in {
    val fm: ListT[Blub, Int] = ListT(Blub(List(1, 2)))
    ListT(Blub(List("hi", "bye"))).flatMap(_ => fm) mustEqual ListT(Blub(List(1, 2, 1, 2)))
  }

  it must "do the for-comprehension thing with all the lists" in {
    val r = for {
      a <- ListT(Blub(List(1, 2)))
      b <- ListT(Blub(List(3, 4)))
    } yield a + b
    r mustEqual ListT(Blub(List(4, 5, 5, 6)))
  }

  it must "do the for-comprehension thing with a nil" in {
    val r = for {
      a <- ListT(Blub(List(1)))
      b <- ListT(Blub(List.empty[Int]))
    } yield a + b
    r mustEqual ListT(Blub(List.empty[Int]))
  }
}
