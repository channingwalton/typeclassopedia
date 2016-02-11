package org.typeclassopedia

import scala.Predef.implicitly

trait MonadPlus[M[_]] extends Monad[M] {
  def mzero[A]: M[A]
  def mplus[A](a: M[A], b: M[A]): M[A]
}

trait MonadPluss {
  implicit class MonadPlusOps[M[_]: MonadPlus, A](m: M[A]) {
    def mplus(b: M[A]): M[A] = implicitly[MonadPlus[M]].mplus(m, b)
  }
}
