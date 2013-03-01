package org.scalazlite

trait Implementations {

  implicit object OptionApplicative extends Monad[Option] {
    def map[A, B](m: Option[A], f: A ⇒ B): Option[B] = m map f
    def <*>[A, B](ma: Option[A], f: Option[A ⇒ B]): Option[B] = for (m ← ma; g ← f) yield g(m)
    def point[A](a: ⇒ A) = Some(a)
    def flatMap[A, B](ma: Option[A], f: A ⇒ Option[B]) = ma flatMap f
  }
}