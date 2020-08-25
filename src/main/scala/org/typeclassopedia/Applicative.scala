package org.typeclassopedia

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
trait Applicative[M[_]] extends Pointed[M] with Functor[M] {
  extension [A, B](m: M[A])
    def <*>(f: M[A => B]): M[B]

    def ⊛(b: M[B]): ApplicativeBuilder[A, B] = new ApplicativeBuilder(m, b)

  class ApplicativeBuilder[A, B](a: M[A], b: M[B]) {
    def apply[C](f: (A, B) => C): M[C] = b <*> a.map(f.curried)

    def ⊛[C](c: M[C]): ApplicativeBuilder3[C] = new ApplicativeBuilder3(c)

    class ApplicativeBuilder3[C](c: M[C]) {
      def apply[D](f: (A, B, C) => D): M[D] = c <*> (b <*> a.map(f.curried))

      class ApplicativeBuilder4[D](d: M[D]) {
        def apply[E](f: (A, B, C, D) => E): M[E] = d <*> (c <*> (b <*> a.map(f.curried)))
      }

    }

    // Continue the pattern for ApplicativeBuilder4/5/…
  }
}
