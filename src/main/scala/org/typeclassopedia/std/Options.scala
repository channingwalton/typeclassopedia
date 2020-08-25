package org.typeclassopedia
package std

import scala.{ None, Option, Some, StringContext }
import scala.Predef.implicitly
import java.lang.{ IllegalArgumentException, String }

trait Options {

  implicit class OptionExtras[T](t: T) {
    def some: Option[T] = Some(t)
  }

  def none[T]: Option[T] = None

  trait OptionFunctor extends Functor[Option] {
    def map[A, B](m: Option[A], f: A => B): Option[B] = m map f
  }

  trait OptionPointed extends Pointed[Option] {
    def point[A](a: => A): Option[A] = Some(a)
  }

  trait OptionCopointed extends Copointed[Option] {
    def extract[A](f: Option[A]): A = f.getOrElse(throw new IllegalArgumentException("Option cannot be None"))
  }

  trait OptionApplicative extends Applicative[Option] {
    def <*>[A, B](ma: Option[A], f: Option[A => B]): Option[B] =
      for {
        m <- ma
        g <- f
      } yield g(m)
  }

  trait OptionAlternative extends Alternative[Option] {
    def empty[A]: Option[A]                           = None
    def <|>[A](a: Option[A], b: Option[A]): Option[A] = if (a.isDefined) a else b
  }

  trait OptionFoldable extends Foldable[Option] {
    def foldMap[A, B](fa: Option[A])(f: A => B)(implicit F: Monoid[B]): B = fa.fold(F.zero)(f)
  }

  trait OptionMonad extends Monad[Option] with OptionApplicative {
    def flatMap[A, B](ma: Option[A], f: A => Option[B]): Option[B] = ma flatMap f
  }

  trait OptionComonad extends Comonad[Option] {
    def duplicate[A](a: Option[A]): Option[Option[A]] = Option(a)
  }

  trait OptionMonadPlus extends MonadPlus[Option] {
    def mzero[A]: Option[A] = None

    def mplus[A](a: Option[A], b: Option[A]): Option[A] = a orElse b
  }

  implicit def optionSemigroup[A: Semigroup]: Semigroup[Option[A]] =
    new Semigroup[Option[A]] {
      def append(a: Option[A], b: Option[A]): Option[A] =
        (a, b) match {
          case (Some(a1), Some(a2)) => Some(implicitly[Semigroup[A]].append(a1, a2))
          case (Some(_), None)      => a
          case (None, Some(_))      => b
          case (None, None)         => None
        }
    }

  trait OptionTraverse extends Traversable[Option] with OptionFunctor with OptionFoldable {
    def traverse[G[_]: Applicative, A, B](fa: Option[A])(f: A => G[B]): G[Option[B]] = {
      val none: Option[B] = None
      fa.fold(none.pure)(v => f(v).map((b: B) => Option(b)))
    }
  }

  trait OptionShow {
    import org.typeclassopedia.extras.Show
    implicit def optionShow[T: Show]: Show[Option[T]] =
      new Show[Option[T]] {
        def showT(t: T): String = implicitly[Show[T]].show(t)
        def show(option: Option[T]): String =
          option.fold("None")(v => s"Option(${showT(v)})")
      }
  }

  trait OptionAll
      extends OptionPointed
      with OptionCopointed
      with OptionTraverse
      with OptionMonad
      with OptionComonad
      with OptionMonadPlus
      with OptionAlternative

  given optionAll as OptionAll
}
