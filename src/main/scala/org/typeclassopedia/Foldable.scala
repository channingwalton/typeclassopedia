package org.typeclassopedia

import scala.Predef.implicitly

trait Foldable[F[_]] {
  def foldMap[A, B](fa: F[A])(f: A => B)(implicit F: Monoid[B]): B
}

trait Foldables {

  implicit class FoldableOps[M[_]: Foldable, A](value: M[A]) {
    def foldMap[B: Monoid](f: A => B): B = implicitly[Foldable[M]].foldMap(value)(f)
  }

}
