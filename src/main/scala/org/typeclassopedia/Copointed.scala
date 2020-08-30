package org.typeclassopedia

import scala.Predef.implicitly

trait Copointed[F[_]] extends Functor[F] {

  extension[A](f: F[A])
    def extract: A

    final def copoint: A = f.extract

    final def copure: A = f.extract
}
