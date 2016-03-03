package org.typeclassopedia.transformers

import scala.{Either, Left, Right}
import org.typeclassopedia._

case class EitherT[M[_], A, B](run: M[Either[A, B]])(implicit monadM: Monad[M]) {

  def map[C](f: B => C): EitherT[M, A,C] = {

    def mapEither(e: Either[A, B]): Either[A, C] = e match {
      case Left(a) => Left(a)
      case Right(b) => Right(f(b))
    }

    val mappedRun: M[Either[A,C]] = monadM.map(run, (e: Either[A, B]) => mapEither(e))
    EitherT(mappedRun)
  }

  def flatMap[C](f: B => EitherT[M, A, C]): EitherT[M, A, C] = {

    def mapMyEither(e: Either[A, B]): M[Either[A, C]] = e match {
      case Left(value) => monadM.point(Left(value))
      case Right(value) => f(value).run
    }

    EitherT(monadM.flatMap(run, mapMyEither))

  }
}
