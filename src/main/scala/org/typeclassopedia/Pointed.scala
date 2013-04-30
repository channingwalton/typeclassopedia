package org.typeclassopedia

trait Pointed[P[_]] extends Functor[P] {

  /**
   * Point creates an F[A] given an A.
   */
  def point[A](a: ⇒ A): P[A]

  /**
   *   An alias for point
   */
  final def pure[A](a: ⇒ A): P[A] = point(a)
}

/**
 *   Implicits to help working with Pointed.
 *   This is imported by Typeclassopedia so that all you need to import is Typeclassopedia._
 */
trait Points {

  /**
   *  Wrap a simple type in a Pointed
   */
  implicit class PointedValueOps[T](value: T) {
    def pure[P[_]: Pointed]: P[T] = implicitly[Pointed[P]].pure(value)
  }
}