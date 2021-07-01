package org.typeclassopedia

import java.lang.String
import scala.StringContext
import scala.Predef.implicitly
import org.typeclassopedia.extras.Show

final case class Blub[T](v: T)

object Blub {

  trait BlubFunctor extends Functor[Blub] {
    extension [A, B](m: Blub[A])
      override def map(f: A => B): Blub[B]
        = Blub(f(m.v))
  }

  trait BlubPointed extends Pointed[Blub] {
    extension [A](a: A)
      def point: Blub[A] = Blub(a)
  }

  trait BlubCopointed extends Copointed[Blub] {
    extension[A](f: Blub[A])
      override def extract: A = f.v
  }

  trait BlubApplicative extends Applicative[Blub] {
    extension[A, B](ma: Blub[A])
      override def <*>(f: Blub[A => B]): Blub[B] = Blub(f.v(ma.v))
  }

  trait BlubMonad extends Monad[Blub] with BlubApplicative {
    extension[A, B](ma: Blub[A]) 
      override def flatMap(f: A => Blub[B]): Blub[B] = f(ma.v)
  }

  trait BlubComonad extends Comonad[Blub] {
    extension[A, B](a: Blub[A])
      override def duplicate: Blub[Blub[A]] = Blub(a)
  }

  trait BlubFoldable extends Foldable[Blub] {
    extension[A, B : Monoid](fa: Blub[A])
      override def foldMap(f: A => B): B = f(fa.v)
  }

  trait BlubTraversable extends Traversable[Blub] {
    extension[F[_]: Applicative, A, B](fa: Blub[A])
      override def traverse(f: A => F[B]): F[Blub[B]] = f(fa.v).map(Blub[B](_))
  }

  given [T: Show] (using Blub[T]) : Show[Blub[T]] with 
      extension( blub : Blub[T]) def show : String = s"A blub of ${b.v.show}"
    

  // TODO compound givens wihtout 'as'
  //given Blubbed as BlubFunctor with BlubApplicative with BlubPointed with BlubCopointed with BlubTraversable with BlubMonad with BlubComonad

}
