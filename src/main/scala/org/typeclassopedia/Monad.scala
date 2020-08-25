package org.typeclassopedia

import scala.Predef.implicitly

/**
  * The concept of a Monad, which is an Applicative.
  * There is nothing magical about Monads, so stare at its methods
  * and you will understand what they offer.
  *
  * Monads also have a few laws:
  * <ul>
  * <li> M.pure(x).flatMap(k) == k(x) where x is a function A=> M[B]
  * </ul>
  */
trait Monad[M[_]] extends Applicative[M] {
  extension[A, B](ma: M[A])
    def flatMap(f: A => M[B]): M[B]

  /**
    * This operator,called <i>bind</i>, is an alias for flatMap.
    * It is included here because it is found in Haskell and you will see it everywhere, or hear
    * about the mystical <i>bind</i>.
    */
    final def >>=(f: A => M[B]): M[B] = ma.flatMap(f)
}
