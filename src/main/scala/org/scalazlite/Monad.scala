package org.scalazlite

/**
 *   The concept of a Monad
 */
trait Monad[M[_]] {
  def pure[A](a: ⇒ A): M[A]
  def flatMap[A, B](ma: M[A], f: A ⇒ M[B]): M[B]
}

/**
 *   Monad implementations for standard types.
 */
trait StandardMonads {
  implicit object OptionMonad extends Monad[Option] {
    def pure[A](a: ⇒ A) = Some(a)
    def flatMap[A, B](ma: Option[A], f: A ⇒ Option[B]) = ma flatMap f
  }

}

/**
 *   Implicits to help working with Monads.
 *   This is imported by ScalazLite so that all you need to import is ScalazLite._
 */
trait Monads extends StandardMonads {

  // enrich a value of M[T] with operations 
  implicit class MonadOps[M[_]: Monad, T](value: M[T]) {
    def flatMap[B](f: T ⇒ M[B]): M[B] = implicitly[Monad[M]].flatMap(value, f)
  }

  // wrap a simple type in a Monad M, if an implicit Monad[M] is available
  implicit class ValueOps[T](value: T) {
    def pure[M[_]](implicit M: Monad[M]): M[T] = M.pure(value)
  }
}