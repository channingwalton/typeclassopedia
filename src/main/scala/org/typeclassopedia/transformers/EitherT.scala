package org.typeclassopedia.transformers

import org.typeclassopedia.Monad

import scala.{Either, Left, Right}

case class EitherT[M[_], A, B](run: M[Either[A, B]])(using monad: Monad[M]) {

  def map[C](f: B => C): EitherT[M, A, C] = {

    def mapEither(e: Either[A, B]): Either[A, C] =
      e match {
        case Left(a)  => Left(a)
        case Right(b) => Right(f(b))
      }

    val mappedRun: M[Either[A, C]] = monad.map(run)((e: Either[A, B]) => mapEither(e))
    EitherT(mappedRun)(using monad)
  }

  def flatMap[C](f: B => EitherT[M, A, C]): EitherT[M, A, C] = {

    def mapMyEither(e: Either[A, B]): M[Either[A, C]] =
      e match {
        case Left(value)  => monad.point(Left(value))
        case Right(value) => f(value).run
      }

    EitherT(monad.flatMap(run)(mapMyEither))(using monad)

  }
}
