package org.typeclassopedia

trait Comonad[W[_]] extends Copointed[W] {

  def duplicate[A](a: W[A]): W[W[A]]

  def extend[A, B](a: W[A])(f: W[A] ⇒ B): W[B] = map(duplicate(a), f)
}

trait Comonads {
  implicit class ComonadOps[W[_]: Comonad, A](value: W[A]) {
    def duplicate = implicitly[Comonad[W]].duplicate(value)
    def extend[B](f: W[A] ⇒ B): W[B] = implicitly[Comonad[W]].extend(value)(f)
  }
}