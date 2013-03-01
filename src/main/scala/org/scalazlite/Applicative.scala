package org.scalazlite

/**
 * The concept of an applicative
 */
trait Applicative[M[_]] extends Functor[M] {
  def <*>[A, B](m: M[A], f: M[A ⇒ B]): M[B]
}

/**
 *   Implicits to help working with Applicative.
 *   This is imported by ScalazLite so that all you need to import is ScalazLite._
 */
trait Applicatives {
  implicit class ApplicativeOps[M[_]: Applicative, T](value: M[T]) {
    def <*>[B](f: M[T ⇒ B]): M[B] = implicitly[Applicative[M]] <*> (value, f)
    def |@|[A](a: M[A]) = new ApplicativeBuilder(value, a)
  }

  class ApplicativeBuilder[M[_]: Applicative, A, B](a: M[A], b: M[B]) {
    def apply[C](f: (A, B) ⇒ C): M[C] = b <*> (implicitly[Functor[M]].map(a, f.curried))
  }
}