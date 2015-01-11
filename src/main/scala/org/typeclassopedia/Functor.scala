package org.typeclassopedia

/**
 * The concept of a Functor.
 * A functor is something that applies a simple function, A ⇒ B, to some F[A] to yield an F[B].
 * You will know this as the map method on collection types. Sometimes,
 * you will see map called fmap.
 *
 * There are a couple of laws associated with a Functor, F:
 * <ul>
 * <li>F map identity == F, where <i>identity</i> is the identity function</li>
 * <li>Given two functions, g and h, F map (g andThen h) == (F map g) map h</li>
 * </ul>
 */
trait Functor[F[_]] {
  def map[A, B](m: F[A], f: A ⇒ B): F[B]

   /**
    * These are the laws that a Functor must obey - thanks Scalaz
    */
  trait FunctorLaws {

    def identity[A](fa: F[A])(implicit FA: Equal[F[A]]): Boolean =
      FA.equal(map[A, A](fa, x => x), fa)

    def composite[A, B, C](fa: F[A], f1: A => B, g1: B => C, f2: B => C)(implicit FC: Equal[F[C]]): Boolean =
      FC.equal(map(map(fa, f1), f2), map(fa, f2 compose f1))
  }

  def laws = new FunctorLaws {}
}

/**
 * Implicits to help working with Functors.
 * This is imported by Typeclassopedia so that all you need to import is Typeclassopedia._
 */
trait Functors {

  /**
   * Add a map method to some F[_], delegating to an implicit Functor[F] map method.
   */
  implicit class FunctorOps[F[_] : Functor, T](value: F[T]) {
    final def map[B](f: T ⇒ B): F[B] = implicitly[Functor[F]].map(value, f)

    /**
     * An alias for map.
     */
    final def fmap[B](f: T ⇒ B): F[B] = map(f)
  }
}
