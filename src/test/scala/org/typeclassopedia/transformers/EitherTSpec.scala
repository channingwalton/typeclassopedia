package org.typeclassopedia.transformers

import java.lang.String
import scala.{Int, Either, Left, Right, StringContext}
import org.scalatest.{Matchers, FlatSpec}
import org.typeclassopedia.Blub

class EitherTSpec extends FlatSpec with Matchers {

  def left[A, B](l: A): Either[A, B] = Left(l)
  def right[A, B](r: B): Either[A, B] = Right(r)

  val aLeft = left[Int, String](5)
  val aRight = right[Int, String]("hi")

  "EitherT" should "map" in {
    EitherT(Blub(aRight)).map(_ ⇒ 1) shouldEqual EitherT(Blub(right[Int, Int](1)))
    EitherT(Blub(aLeft)).map(_ ⇒ 1) shouldEqual EitherT(Blub(aLeft))
  }

  it should "flatMap" in {
    val fm: EitherT[Blub, Int, String] = EitherT(Blub(aLeft))
    EitherT(Blub(aRight)).flatMap(_ ⇒ fm) shouldEqual EitherT(Blub(aLeft))
  }

  it should "do the for-comprehension thing with all the rights" in {
    val r = for {
      a ← EitherT(Blub((right[Int, String]("hi"))))
      b ← EitherT(Blub(right[Int, String]("bye")))
    } yield s"$a $b"
    r shouldEqual EitherT(Blub(right[Int, String]("hi bye")))
  }

  it should "do the for-comprehension thing with a left" in {
    val r = for {
      a ← EitherT(Blub(aRight))
      b ← EitherT(Blub(aLeft))
    } yield s"$a $b"
    r shouldEqual EitherT(Blub(aLeft))
  }

}
