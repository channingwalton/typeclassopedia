package org.typeclassopedia

/**
class Applicative f ⇒ Alternative f where
   empty :: f a
   (<|>) :: f a → f a → f a
 */
trait Alternative[F[_]] extends Applicative[F] {
  def empty[A](implicit ev: Monoid[F[A]]): F[A]
  def <|>[A](a: F[A], b: F[A])(implicit ev: Monoid[F[A]]): F[A]
}