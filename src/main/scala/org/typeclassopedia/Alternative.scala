package org.typeclassopedia

/**
class Applicative f ⇒ Alternative f where
   empty :: f a
   (<|>) :: f a → f a → f a
 */
trait Alternative[F[_]] extends Applicative[F] {
  def empty[A]: F[A]
  def <|>[A](a: F[A], b: F[A]): F[A]
}

trait Alternatives {
  implicit class AlternativeOps[F[_]: Alternative, A](a: F[A]) {
    def <|>(b: F[A]): F[A] = implicitly[Alternative[F]].<|>(a, b)
  }
}