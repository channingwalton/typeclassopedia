package org.typeclassopedia

import scala.Predef.implicitly
import scala.Boolean

/**
  * The concept of a Functor.
  * A functor is something that applies a simple function, A => B, to some F[A] to yield an F[B].
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
  extension [A, B](x: F[A])
    def map(f: A => B): F[B]

  /**
   * These are the laws that a Functor must obey - thanks Scalaz
   */
  trait FunctorLaws {

    def identity[A](fa: F[A])(using FA: Equal[F[A]]): Boolean =
      FA.equal(fa.map(x => x), fa)

    def composite[A, B, C](fa: F[A], f1: A => B, f2: B => C)(using FC: Equal[F[C]]): Boolean =
      FC.equal(fa.map(f1).map(f2), fa.map(f1 andThen f2))
  }

  def laws: FunctorLaws = new FunctorLaws {}

}
