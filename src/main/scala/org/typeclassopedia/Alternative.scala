package org.typeclassopedia

/**
class Applicative f ⇒ Alternative f where
   empty :: f a
   (<|>) :: f a → f a → f a
 */
trait Alternative[F[_]] extends Applicative[F] {
  def monoid[A]: Monoid[F[A]]
  def empty[A]: F[A]
  def <|>[A](a: F[A], b: F[A]): F[A] = monoid.append(a, b)
}