package org.typeclassopedia
package std

import scala.{Conversion, None, Option, Some, StringContext}
import org.typeclassopedia.extras.Show
import scala.Predef.implicitly
import java.lang.{IllegalArgumentException, String}

object Options {

  extension[T](t: T) {
    def some: Option[T] = Some(t)
  }

  def none[T]: Option[T] = None

  trait OptionFunctor extends Functor[Option] {
    extension [A, B](x: Option[A])
      override def map(f: A => B): Option[B] = x map f
  }

  trait OptionPointed extends Pointed[Option] {
    extension [A](a: A)
      override def point: Option[A] = Some(a)
  }

  trait OptionCopointed extends Copointed[Option] {
    extension[A](f: Option[A])
      override def extract: A =
        f.getOrElse(throw new IllegalArgumentException("Option cannot be None"))
  }

  trait OptionApplicative extends Applicative[Option] {
    extension [A, B](ma: Option[A])
      def <*>(f: Option[A => B]): Option[B] = 
        for {
          m <- ma
          g <- f
        } yield g(m)
  }

  trait OptionAlternative extends Alternative[Option] {
    def empty[A]: Option[A]                           = None

    extension [A, B](a: Option[A])
      override def <|>(b: Option[A]): Option[A] = if (a.isDefined) a else b
  }

  trait OptionFoldable extends Foldable[Option] {
    extension[A, B](value: Option[A])
      override def foldMap(f: A => B)(using monoid: Monoid[B]): B =
        value.fold(monoid.zero)(f)
  }

  trait OptionMonad extends Monad[Option] with OptionApplicative {
    extension[A, B](ma: Option[A])
      override def flatMap(f: A => Option[B]): Option[B] =
        ma flatMap f
  }

  trait OptionComonad extends Comonad[Option] {
    extension[A, B](a: Option[A])
      override def duplicate: Option[Option[A]] = Option(a)
  }

  trait OptionMonadPlus extends MonadPlus[Option] {
    def mzero[A]: Option[A] = None

    extension[A](a: Option[A])
      override def mplus(b: Option[A]): Option[A] = a orElse b
  }


  // [A: Semigroup] using replaces 'as' as the given type to summon
  given [A: Semigroup](using Option[A]) : Semigroup[Option[A]] =
    new Semigroup[Option[A]] {
      extension(a: Option[A])
        override def append(b: Option[A]): Option[A] =
          (a, b) match {
            case (Some(a1), Some(a2)) => Some(a1.append(a2))
            case (Some(_), None)      => a
            case (None, Some(_))      => b
            case (None, None)         => None
          }
    }

  trait OptionTraverse extends Traversable[Option] with OptionFunctor with OptionFoldable {
    extension[G[_]: Applicative, A, B](fa: Option[A])
      override def traverse(f: A => G[B]): G[Option[B]] = {
        val none: Option[B] = None
        fa.fold(none.pure)(v => f(v).map((b: B) => Option(b)))
      }
  }

  trait OptionShow {
    given [T : Show](using Option[T]) : Show[Option[T]] with
      extension(option : Option[T]) def show : String =
          option.fold("None")(v => s"Option(${v.show})")
  }

  trait OptionAll
      extends OptionPointed
      with OptionApplicative
      with OptionCopointed
      with OptionTraverse
      with OptionMonad
      with OptionComonad
      with OptionMonadPlus
      with OptionAlternative
      with OptionShow

  //  TODO 
  // given optionAll : OptionAll

}
