package org.scalazlite

/**
 * The concept of an applicative functor.
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
    final def <*>[B](f: M[T ⇒ B]): M[B] = implicitly[Applicative[M]] <*> (value, f)

    /**
     * This method simplifies working with applicatives.
     * For example, instead of
     * {{{
     * val addInts = ( (a:Int, b:Int, c:Int) => a + b + c ).curried
     * val sum = x <*> (y <*> (z map addInts))
     * }}}
     * do
     * {{{
     * (x |@| y |@| z) {_ + _ + _}
     * }}}
     */
    final def |@|[A](a: M[A]) = new ApplicativeBuilder(value, a)
  }

  class ApplicativeBuilder[M[_]: Applicative, A, B](a: M[A], b: M[B]) {
    def apply[C](f: (A, B) ⇒ C): M[C] = b <*> (implicitly[Functor[M]].map(a, f.curried))

    def |@|[C](c: M[C]) = new ApplicativeBuilder3(c)

    class ApplicativeBuilder3[C](c: M[C]) {
      def apply[D](f: (A, B, C) ⇒ D): M[D] = c <*> (b <*> (implicitly[Functor[M]].map(a, f.curried)))
    }
  }
}