package org.typeclassopedia.transformers

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.typeclassopedia.Blub

class EitherTSpec extends AnyFlatSpec with Matchers {

  def left[A, B](l: A): Either[A, B]  = Left(l)
  def right[A, B](r: B): Either[A, B] = Right(r)

  val aLeft  = left[Int, String](5)
  val aRight = right[Int, String]("hi")

  "EitherT" must "map" in {
    EitherT(Blub(aRight)).map(_ => 1) mustEqual EitherT(Blub(right[Int, Int](1)))
    EitherT(Blub(aLeft)).map(_ => 1) mustEqual EitherT(Blub(aLeft))
  }

  it must "flatMap" in {
    val fm: EitherT[Blub, Int, String] = EitherT(Blub(aLeft))
    EitherT(Blub(aRight)).flatMap(_ => fm) mustEqual EitherT(Blub(aLeft))
  }

  it must "do the for-comprehension thing with all the rights" in {
    val r = for {
      a <- EitherT(Blub((right[Int, String]("hi"))))
      b <- EitherT(Blub(right[Int, String]("bye")))
    } yield s"$a $b"
    r mustEqual EitherT(Blub(right[Int, String]("hi bye")))
  }

  it must "do the for-comprehension thing with a left" in {
    val r = for {
      a <- EitherT(Blub(aRight))
      b <- EitherT(Blub(aLeft))
    } yield s"$a $b"
    r mustEqual EitherT(Blub(aLeft))
  }

}
