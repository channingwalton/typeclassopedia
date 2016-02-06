package org.typeclassopedia.extras

/**
  * See http://typelevel.org/blog/2016/02/04/variance-and-functors.html
  */
trait ContravariantFunctor[F[_]] {
  def contramap[A, B](fa: F[A], f: B => A): F[B]
}

object ContravariantFunctor {
  implicit object ContraShow extends ContravariantFunctor[Show] {
    def contramap[A, B](fa: Show[A], f: B => A): Show[B] =
      new Show[B] {
        def show(t: B): String = fa.show(f(t))
      }
  }
}
