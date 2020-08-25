package org.typeclassopedia.transformers

import org.typeclassopedia.Monad

import scala.{Either, Left, Right}

case class EitherT[M[_]: Monad, A, B](run: M[Either[A, B]]) {

  def map[C](f: B => C): EitherT[M, A, C] = {

    def mapEither(e: Either[A, B]): Either[A, C] =
      e match {
        case Left(a)  => Left(a)
        case Right(b) => Right(f(b))
      }

    val mappedRun: M[Either[A, C]] = run.map((e: Either[A, B]) => mapEither(e))
    EitherT(mappedRun)
  }

  def flatMap[C](f: B => EitherT[M, A, C]): EitherT[M, A, C] = {

    def mapMyEither(e: Either[A, B]): M[Either[A, C]] =
      e match {
        case Left(value)  => Left(value).point
        case Right(value) => f(value).run
      }

    EitherT(run.flatMap(mapMyEither))

  }
}
