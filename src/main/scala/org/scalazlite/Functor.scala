package org.scalazlite

/**
 *   The concept of a Functor
 */
trait Functor[F[_]] {
  def map[A, B](m: F[A], f: A ⇒ B): F[B]
  def point[A](a: ⇒ A): F[A]
  // alias for point
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

  // wrap a simple type in a Functor F, if an implicit Monad[M] is available
  implicit class FunctorValueOps[T](value: T) {
    def pure[F[_]](implicit F: Functor[F]): F[T] = F.pure(value)
  }
}