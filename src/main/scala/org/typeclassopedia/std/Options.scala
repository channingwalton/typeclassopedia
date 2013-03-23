package org.typeclassopedia
package std

trait Options {

  implicit class OptionExtras[T](t: T) {
    def some: Option[T] = Some(t)
  }

  def none[T]: Option[T] = None

  trait OptionFunctor extends Functor[Option] {
    def map[A, B](m: Option[A], f: A ⇒ B): Option[B] = m map f
    def point[A](a: ⇒ A) = Some(a)
  }

  trait OptionApplicative extends Applicative[Option] {
    def <*>[A, B](ma: Option[A], f: Option[A ⇒ B]): Option[B] = for (m ← ma; g ← f) yield g(m)
  }

  trait OptionFoldable extends Foldable[Option] {
    def foldMap[A, B](fa: Option[A])(f: A ⇒ B)(implicit F: Monoid[B]): B = fa.fold(F.zero)(f)
  }

  trait OptionMonad extends Monad[Option] with OptionApplicative {
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

  trait OptionTraverse extends Traversable[Option] with OptionFunctor with OptionFoldable {
    def traverse[G[_]: Applicative, A, B](fa: Option[A])(f: A ⇒ G[B]): G[Option[B]] = {
      val none: Option[B] = None
      val gapp = implicitly[Applicative[G]]
      fa.fold(gapp.pure(none))(v ⇒ gapp.map(f(v), (b: B) ⇒ Option(b)))
    }
  }

  implicit object OptionAll extends OptionTraverse with OptionMonad
}