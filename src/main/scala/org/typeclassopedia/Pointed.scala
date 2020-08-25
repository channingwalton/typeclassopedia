package org.typeclassopedia

import scala.Predef.implicitly

/**
  * The Pointed type class represents pointed functors.
  * The Pointed class represents the additional ability to put a value into a default context.
  */
trait Pointed[F[_]] extends Functor[F] {

  /**
    * Point creates an F[A] given an A.
    */
  extension [A](a: A)
    def point: F[A]
  
    /**
      * An alias for point
      */
    final def pure: F[A] = a.point
}

