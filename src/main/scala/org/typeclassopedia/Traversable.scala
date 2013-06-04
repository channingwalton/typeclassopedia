package org.typeclassopedia

trait Traversable[M[_]] extends Functor[M] with Foldable[M] {

  def traverse[G[_] : Applicative, A, B](fa: M[A])(f: A ⇒ G[B]): G[M[B]]

  def sequence[G[_] : Applicative, A](fga: M[G[A]]): G[M[A]] = traverse[G, G[A], A](fga)(identity)
}

/**
 * Implicits to help working with Traversables.
 * This is imported by Typeclassopedia so that all you need to import is Typeclassopedia._
 */
trait Traversables {

  implicit class TraversableOps[M[_] : Traversable, T](value: M[T]) {
    def traverse[G[_] : Applicative, B](f: T ⇒ G[B]): G[M[B]] = implicitly[Traversable[M]].traverse(value)(f)
  }

  implicit class SequenceOps[M[_] : Traversable, G[_]: Applicative, T](value: M[G[T]]) {
    def sequence: G[M[T]] = implicitly[Traversable[M]].sequence[G, T](value)
  }

}