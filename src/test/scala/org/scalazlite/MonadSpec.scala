package org.scalazlite

import org.scalatest._
import ScalazLite._

class MonadSpec extends FlatSpec {

  "A monad" should "flatMap that thang" in {
    // I'll use >>= instead so we know scalaz-lite is being used
    val x: Option[Int] = Some(1)
    (x >>= (x ⇒ Some(x + 1))) === Some(2)
  }

  it should "support new monads" in {
    case class Blub[T](v: T)
    implicit object BlubMonad extends Monad[Blub] {
      def pure[A](a: ⇒ A) = Blub(a)
      def flatMap[A, B](ma: Blub[A], f: A ⇒ Blub[B]) = f(ma.v)
    }

    (Blub(1) >>= (x ⇒ Blub(x + 1))) === Blub(2)
  }
}