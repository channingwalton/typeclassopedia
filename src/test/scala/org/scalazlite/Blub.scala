package org.scalazlite

case class Blub[T](v: T)

object Blub {
  implicit object BlubMonad extends Monad[Blub] {
    def map[A, B](m: Blub[A], f: A ⇒ B): Blub[B] = Blub(f(m.v))
    def <*>[A, B](ma: Blub[A], f: Blub[A ⇒ B]): Blub[B] = Blub(f.v(ma.v))
    def point[A](a: ⇒ A) = Blub(a)
    def flatMap[A, B](ma: Blub[A], f: A ⇒ Blub[B]) = f(ma.v)
  }
}