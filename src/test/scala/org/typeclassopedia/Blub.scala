package org.typeclassopedia

case class Blub[T](v: T)

object Blub {

  trait BlubFunctor extends Functor[Blub] {
    def map[A, B](m: Blub[A], f: A ⇒ B): Blub[B] = Blub(f(m.v))
  }

  trait BlubPointed extends Pointed[Blub] {
    def point[A](a: ⇒ A) = Blub(a)
  }

  trait BlubCopointed extends Copointed[Blub] {
    def extract[A](f: Blub[A]): A = f.v
  }

  trait BlubApplicative extends Applicative[Blub] {
    def <*>[A, B](ma: Blub[A], f: Blub[A ⇒ B]): Blub[B] = Blub(f.v(ma.v))
  }

  trait BlubMonad extends Monad[Blub] with BlubApplicative {
    def flatMap[A, B](ma: Blub[A], f: A ⇒ Blub[B]) = f(ma.v)
  }

  trait BlubFoldable extends Foldable[Blub] {
    def foldMap[A, B](fa: Blub[A])(f: A ⇒ B)(implicit F: Monoid[B]): B = f(fa.v)
  }

  trait BlubTraversable extends Traversable[Blub] with BlubFunctor with BlubFoldable {
    def traverse[G[_] : Applicative, A, B](fa: Blub[A])(f: A ⇒ G[B]): G[Blub[B]] = {
      implicitly[Applicative[G]].map(f(fa.v), Blub apply)
    }
  }

  implicit object Blubbed extends BlubPointed with BlubCopointed with BlubTraversable with BlubMonad

}