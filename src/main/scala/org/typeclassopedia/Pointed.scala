package org.typeclassopedia

import scala.Predef.implicitly

/**
  * The Pointed type class represents pointed functors.
  * The Pointed class represents the additional ability to put a value into a default context.
  */
trait Pointed[F[_]] extends Functor[F] {

  /**
    * Point creates an F[A] given an A.
    */
  def point[A](a: => A): F[A]

  /**
    * An alias for point
    */
  final def pure[A](a: => A): F[A] = point(a)
}

/**
  * Implicits to help working with Pointed.
  * This is imported by Typeclassopedia so that all you need to import is Typeclassopedia._
  */
trait Points {

  /**
    * Wrap a simple type in a Pointed
    */
  implicit class PointedValueOps[T](value: T) {
    def pure[P[_]: Pointed]: P[T] = implicitly[Pointed[P]].pure(value)
  }

}
