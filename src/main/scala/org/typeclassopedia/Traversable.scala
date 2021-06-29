package org.typeclassopedia

import scala.Predef.{ identity, implicitly }

trait Traversable[T[_]] {

  extension[F[_] : Applicative, A, B](fa: T[A])
    def traverse(f: A => F[B]): F[T[B]]

  extension[F[_]: Applicative, A](fga: T[F[A]])
    def sequenceA: F[T[A]] = fga.traverse(identity)
    def sequence: F[T[A]]  = fga.sequenceA

  extension[M[_]: Applicative, A, B](value: T[A])
    def mapM(f: A => M[B]): M[T[B]] = value.traverse(f)
}
