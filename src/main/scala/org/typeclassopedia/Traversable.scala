package org.typeclassopedia

trait Traversable[T[_]] extends Functor[T] with Foldable[T] {

  def traverse[F[_] : Applicative, A, B](fa: T[A])(f: A ⇒ F[B]): F[T[B]]

  def sequenceA[F[_] : Applicative, A](fga: T[F[A]]): F[T[A]] = traverse[F, F[A], A](fga)(identity)

  def mapM[M[_]: Monad, A, B](f: A ⇒ M[B])(v: T[A]): M[T[B]] = traverse(v)(f)

  def sequence[M[_] : Monad, A](mga: T[M[A]]): M[T[A]] = traverse[M, M[A], A](mga)(identity)
}

/**
 * Implicits to help working with Traversables.
 * This is imported by Typeclassopedia so that all you need to import is Typeclassopedia._
 */
trait Traversables {

  implicit class TraversableOps[T[_] : Traversable, A](value: T[A]) {
    def traverse[F[_] : Applicative, B](f: A ⇒ F[B]): F[T[B]] = implicitly[Traversable[T]].traverse(value)(f)
  }

  implicit class SequenceAOps[T[_] : Traversable, F[_]: Applicative, A](value: T[F[A]]) {
    def sequenceA: F[T[A]] = implicitly[Traversable[T]].sequenceA[F, A](value)
    def sequence: F[T[A]] = sequenceA
  }

  implicit class MapMOps[T[_] : Traversable, A](value: T[A]) {
    def mapM[B, M[_]: Monad](f: A ⇒ M[B]): M[T[B]] = implicitly[Traversable[T]].mapM[M, A, B](f)(value)
  }

}