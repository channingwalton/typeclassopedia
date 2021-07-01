package org.typeclassopedia.extras

import java.lang.String

/**
  * See http://typelevel.org/blog/2016/02/04/variance-and-functors.html
  */
trait ContravariantFunctor[F[_]] {
  extension[A, B](fa: F[A])
    def contramap(f: B => A): F[B]
}

// TODO - advanced challenge :-)
// given ContravariantFunctor[Show] with
//   extension[A, B](fa: Show[A])(using t : Show[B])
//     def contramap(f: B => A): Show[B] = fa.show(f(t))
      