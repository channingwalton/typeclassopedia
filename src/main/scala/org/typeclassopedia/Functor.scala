package org.typeclassopedia

/**
 *   The concept of a Functor.
 *   A functor is something that applies a simple function, A ⇒ B, to some F[A] to yield an F[B].
 *   You will know this as the map method on collection types. Sometimes,
 *   you will see map called fmap.
 *
 *  There are a couple of laws associated with a Functor, F:
 *  <ul>
 *  <li>F map identity == F</li>
 *  <li>Given two functions, g and h, F map (g andThen h) == (F map g) map h</li>
 *  </ul>
 */
trait Functor[F[_]] {
  def map[A, B](m: F[A], f: A ⇒ B): F[B]
}

/**
 *   Implicits to help working with Functors.
 *   This is imported by Typeclassopedia so that all you need to import is Typeclassopedia._
 */
trait Functors {

  /**
   * Add a map method to some F[_], delegating to a Functor[F] map method.
   */
  implicit class FunctorOps[F[_]: Functor, T](value: F[T]) {
    def map[B](f: T ⇒ B): F[B] = implicitly[Functor[F]].map(value, f)
    final def fmap[B](f: T ⇒ B): F[B] = map(f)
  }

}