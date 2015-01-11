package org.typeclassopedia.std

import org.typeclassopedia.{ArrowApply, ArrowChoice, Category}

/**
 * This contains implementations of [[org.typeclassopedia.Category Category[Function1] ]]
 */
trait Function1Categories {

  trait Function1Category extends Category[Function1] {
    def id[A]: A ⇒ A = identity

    def compose[A, B, C](f: B ⇒ C, g: A ⇒ B): A ⇒ C = f compose g
  }

  trait Function1Arrow extends ArrowChoice[Function1] {
    def arr[B, C](f: B ⇒ C): B ⇒ C = f

    def first[B, C, D](b: B ⇒ C): ((B, D)) ⇒ (C, D) = (bd: (B, D)) ⇒ (b(bd._1), bd._2)
  }

  trait Function1ArrowApply extends ArrowApply[Function1] {
    def app[B, C]: ((B ⇒ C, B)) ⇒ C = (bc: (B ⇒ C, B)) ⇒ bc._1(bc._2)
  }

  trait Function1Choice extends ArrowChoice[Function1] {
    def left[B, C, D](f: B ⇒ C): Either[B, D] ⇒ Either[C, D] = {
      case Left(v) ⇒ Left[C, D](f(v))
      case Right(v) ⇒ Right[C, D](v)
    }

    def right[B, C, D](f: B ⇒ C): Either[D, B] ⇒ Either[D, C] = {
      case Left(v) ⇒ Left[D, C](v)
      case Right(v) ⇒ Right[D, C](f(v))
    }

    def +++[B, C, B2, C2](a: B ⇒ C, b: B2 ⇒ C2): Either[B, B2] ⇒ Either[C, C2] = {
      case Left(v) ⇒ Left[C, C2](a(v))
      case Right(v) ⇒ Right[C, C2](b(v))
    }

    def |||[B, C, D](a: B ⇒ D, b: C ⇒ D) = {
      case Left(v) ⇒ a(v)
      case Right(v) ⇒ b(v)
    }
  }

  implicit object Function1Arrows extends Function1Category with Function1Arrow with Function1ArrowApply with Function1Choice

}
