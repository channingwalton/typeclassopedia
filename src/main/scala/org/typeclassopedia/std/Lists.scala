package org.typeclassopedia
package std

import scala.{ IllegalArgumentException, List, Nil }
import scala.Predef.implicitly

object Lists {

  trait ListFunctor extends Functor[List] {
    extension [A, B](m: List[A])
      override def map(f: A => B): List[B] = m map f
  }

  trait ListPointed extends Pointed[List] {
    extension [A](a: A)
      override def point: List[A] = List(a)
  }

  trait ListCopointed extends Copointed[List] {
    extension[A](f: List[A])
      override def extract: A =
        f.headOption.getOrElse(throw new IllegalArgumentException("the list cannot be empty"))
  }

  trait ListApplicative extends Applicative[List] {
    extension [A, B](ma: List[A])
      override def <*>(f: List[A => B]): List[B] =
        for {
          m <- ma
          g <- f
        } yield g(m)
  }

  trait ListAlternative extends Alternative[List] {
    def empty[A]: List[A] = Nil

    extension [A, B](a: List[A])
      override def <|>(b: List[A]): List[A] = a ++ b
  }

  trait ListMonad extends Monad[List] with ListApplicative {
    extension[A, B](ma: List[A])
      override def flatMap(f: A => List[B]): List[B] = ma flatMap f
  }

  trait ListComonad extends Comonad[List] {
    extension[A, B](a: List[A])
      override def duplicate: List[List[A]] = List(a)
  }

  trait ListMonadPlus extends MonadPlus[List] {
    def mzero[A]: List[A] = Nil

    extension[A](a: List[A])
      override def mplus(b: List[A]): List[A] = a ++ b
  }

  trait ListFoldable extends Foldable[List] {
    extension[A, B](value: List[A])
      override def foldMap(f: A => B)(using monoid: Monoid[B]): B =
        value.foldLeft(monoid.zero)((b, a) => b |+| f(a))
  }

  given listSemi[A]: Semigroup[List[A]] =
    new Semigroup[List[A]] {
      extension(a: List[A])
        def append(b: List[A]): List[A] = a ::: b
    }

  trait ListTraverse extends Traversable[List] with ListFunctor with ListFoldable {
    extension[G[_]: Applicative, A, B](fa: List[A])
      override def traverse(f: A => G[B]): G[List[B]] = {
        // a nil of the right type
        val nil: List[B] = Nil

        // first map fa, a List[A] with g to get a List[G[B]]
        val lGB: List[G[B]] = fa map f

        // use the applicative for G to fold the list, List[G[B]], to build a G[List[B]]
        val app = (a: List[B]) => (b: B) => a :+ b
        lGB.foldLeft(nil.point)((acc, gb) => gb <*> acc.map(app))
      }
  }

  trait ListAll
      extends ListPointed
      with ListApplicative
      with ListCopointed
      with ListComonad
      with ListTraverse
      with ListMonad
      with ListMonadPlus
      with ListAlternative
      with ListFoldable

  given listAll: ListAll = new ListAll {}
}
