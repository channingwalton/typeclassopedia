package org.scalazlite

/**
 *   The concept of a Functor
 */
trait Functor[M[_]] {
  def map[A, B](m: M[A], f: A ⇒ B): M[B]
}

/**
 *  Functor implementations for standard types.
 */
trait Functors {
  implicit object OptionFunctor extends Functor[Option] {
    def map[A, B](m: Option[A], f: A ⇒ B): Option[B] = m map f
  }
}

/**
 *   Implicits to help working with Functors.
 *   This is imported by ScalazLite so that all you need to import is ScalazLite._
 */

object Functor {
  implicit class FunctorOps[M[_]: Functor, T](value: M[T]) {
    def map[B](f: T ⇒ B): M[B] = implicitly[Functor[M]].map(value, f)
  }
}