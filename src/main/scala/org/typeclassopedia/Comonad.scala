package org.typeclassopedia

import scala.Predef.implicitly

trait Comonad[W[_]] extends Copointed[W] {

  extension[A, B](a: W[A])
    def duplicate: W[W[A]]
    final def extend(f: W[A] => B): W[B] = a.duplicate.map(f)
}
