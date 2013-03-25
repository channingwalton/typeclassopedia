package org.typeclassopedia

trait Alternative[M[_]] extends Applicative[M] {
  def <|>[A: Monoid](m: M[A], mb: M[A]): M[A]
}
