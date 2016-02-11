package org.typeclassopedia

import scala.Predef.implicitly

/**
 * The concept of an applicative functor.
 * Brent Yorgey's description is as follows:
 * <pre>
 * Recall that Functor allows us to lift a “normal” function to a function on computational contexts.
 * But fmap [map] doesn't allow us to apply a function which is itself in a context to a value in another context.
 * Applicative gives us just such a tool. Note that every Applicative must also be a Functor.
 * </pre>
 * There is a law associated with Applicatives which states
 * <pre>
 * Give an Applicative x: X, x map g == x <*> X.pure(g)
 * </pre>
 */
trait Applicative[M[_]] extends Pointed[M] {
  def <*>[A, B](m: M[A], f: M[A ⇒ B]): M[B]
}

/**
 * Implicits to help working with Applicative.
 * This is imported by Typeclassopedia so that all you need to import is Typeclassopedia._
 */
trait Applicatives {

  implicit class ApplicativeOps[M[_] : Applicative, T](value: M[T]) {
    final def <*>[B](f: M[T ⇒ B]): M[B] = implicitly[Applicative[M]] <*>(value, f)

    /**
     * This method simplifies working with applicatives.
     * For example, instead of
     * {{{
     * val addInts = ( (a:Int, b:Int, c:Int)⇒ a + b + c ).curried
     * val sum = x <*> (y <*> (z map addInts))
     * }}}
     * do
     * {{{
     * (x ⊛ y ⊛ z) {_ + _ + _}
     * }}}
     */
    final def ⊛[A](a: M[A]): ApplicativeBuilder[M, T, A] = new ApplicativeBuilder(value, a)
  }

  class ApplicativeBuilder[M[_] : Applicative, A, B](a: M[A], b: M[B]) {
    def apply[C](f: (A, B) ⇒ C): M[C] = b <*> implicitly[Functor[M]].map(a, f.curried)

    def ⊛[C](c: M[C]): ApplicativeBuilder3[C] = new ApplicativeBuilder3(c)

    class ApplicativeBuilder3[C](c: M[C]) {
      def apply[D](f: (A, B, C) ⇒ D): M[D] = c <*> (b <*> implicitly[Functor[M]].map(a, f.curried))

      class ApplicativeBuilder4[D](d: M[D]) {
        def apply[E](f: (A, B, C, D) ⇒ E): M[E] = d <*> (c <*> (b <*> implicitly[Functor[M]].map(a, f.curried)))
      }

    }

    // Continue the pattern for ApplicativeBuilder4/5/…
  }

}
