package org.typeclassopedia

trait Implementations {

  implicit object OptionApplicative extends Monad[Option] {
    def map[A, B](m: Option[A], f: A ⇒ B): Option[B] = m map f
    def <*>[A, B](ma: Option[A], f: Option[A ⇒ B]): Option[B] = for (m ← ma; g ← f) yield g(m)
    def point[A](a: ⇒ A) = Some(a)
    def flatMap[A, B](ma: Option[A], f: A ⇒ Option[B]) = ma flatMap f
  }

  implicit def optionSemigroup[A: Semigroup]: Semigroup[Option[A]] = new Semigroup[Option[A]] {
    def append(a: Option[A], b: Option[A]) = (a, b) match {
      case (Some(a1), Some(a2)) ⇒ Some(implicitly[Semigroup[A]].append(a1, a2))
      case (Some(a1), None)     ⇒ a
      case (None, Some(a2))     ⇒ b
      case (None, None)         ⇒ None
    }
  }

  implicit object ListApplicative extends Monad[List] {
    def map[A, B](m: List[A], f: A ⇒ B): List[B] = m map f
    def <*>[A, B](ma: List[A], f: List[A ⇒ B]): List[B] = for (m ← ma; g ← f) yield g(m)
    def point[A](a: ⇒ A) = List(a)
    def flatMap[A, B](ma: List[A], f: A ⇒ List[B]) = ma flatMap f
  }

  implicit def listSemigroup[A: Semigroup]: Semigroup[List[A]] = new Semigroup[List[A]] {
    def append(a: List[A], b: List[A]) = a ::: b
  }

  implicit object ListFoldable extends Foldable[List] {
    def foldMap[A, B](fa: List[A])(f: A ⇒ B)(implicit F: Monoid[B]): B = fa.foldLeft(F.zero)((b, a) ⇒ F.append(b, f(a)))
  }

  implicit object MonoidInt extends Monoid[Int] {
    def zero = 0
    def append(a1: Int, a2: Int): Int = a1 + a2
  }

  implicit object MonoidString extends Semigroup[String] {
    def zero = ""
    def append(a1: String, a2: String): String = a1 + a2
  }
  implicit class OptionExtras[T](t: T) {
    def some: Option[T] = Some(t)
  }
}