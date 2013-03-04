package org.scalazlite

/**
 *   The concept of a Functor.
 *   A functor is something that uses a simple function A ⇒ B and applies it to some F[A] yield an F[B].
 *   You will be know this as the map method on collection types and Option.
 */
trait Functor[F[_]] {
  def map[A, B](m: F[A], f: A ⇒ B): F[B]

  /**
   * Point creates an F[A] given an A
   */
  def point[A](a: ⇒ A): F[A]
  /**
   *   An alias for point
   */
  def pure[A](a: ⇒ A): F[A] = point(a)
}

/**
 *   Implicits to help working with Functors.
 *   This is imported by ScalazLite so that all you need to import is ScalazLite._
 */
trait Functors {
  implicit class FunctorOps[F[_]: Functor, T](value: F[T]) {
    def map[B](f: T ⇒ B): F[B] = implicitly[Functor[F]].map(value, f)
  }

  /**
   *  Wrap a simple type in a Functor F
   */
  implicit class FunctorValueOps[T](value: T) {
    def pure[F[_]](implicit F: Functor[F]): F[T] = F.pure(value)
  }
}