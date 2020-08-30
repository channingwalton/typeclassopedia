package org.typeclassopedia

import scala.Predef.implicitly

trait MonadPlus[M[_]] extends Monad[M] {
  def mzero[A]: M[A]
  
  extension[A](a: M[A])
    def mplus(b: M[A]): M[A]
}
