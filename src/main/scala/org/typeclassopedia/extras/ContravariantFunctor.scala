package org.typeclassopedia.extras

import java.lang.String

/**
  * See http://typelevel.org/blog/2016/02/04/variance-and-functors.html
  */
trait ContravariantFunctor[F[_]] {
  extension[A, B](fa: F[A])
    def contramap(f: B => A): F[B]
}

given contraShow as ContravariantFunctor[Show] {
  extension[A, B](fa: Show[A])
    override def contramap(f: B => A): Show[B] =
      new Show[B] {
        override def show(t: B): String =
          fa.show(f(t))
      }
}
