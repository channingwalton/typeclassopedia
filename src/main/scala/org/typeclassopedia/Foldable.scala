package org.typeclassopedia

import scala.Predef.implicitly

trait Foldable[F[_]] {
  extension[A, B: Monoid](value: F[A])
    def foldMap(f: A => B): B
}
