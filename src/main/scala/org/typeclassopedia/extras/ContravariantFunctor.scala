package org.typeclassopedia.extras

import java.lang.String

/**
  * See http://typelevel.org/blog/2016/02/04/variance-and-functors.html
  */
trait ContravariantFunctor[F[_]] {
  extension[A, B](fa: F[A])
    def contramap(f: B => A): F[B]
}


given ContravariantFunctor[Show] with
  extension[A, B](fa: Show[A])
    def contramap(f: B => A): Show[B] =
      new Show[B] {
        def show(t: B): String =
          fa.show(f(t))
      }

