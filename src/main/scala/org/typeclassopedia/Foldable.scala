package org.typeclassopedia

import scala.Predef.implicitly

trait Foldable[F[_]] {
  extension[A, B](value: F[A])
    def foldMap(f: A => B)(using monoid: Monoid[B]): B
}
