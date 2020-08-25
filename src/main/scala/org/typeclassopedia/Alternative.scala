package org.typeclassopedia
import scala.Predef.implicitly

trait Alternative[F[_]] extends Applicative[F] {
  def empty[A]: F[A]

  extension [A, B](a: F[A])
    def <|>(b: F[A]): F[A]
}
