package org.typeclassopedia.transformers

import scala.{ Boolean, None, Option, Some }

import org.typeclassopedia.Monad

/**
  * The type OptionT[M[_], A] is a monad transformer that represents M[Option[A]].
  * The original code came from http://stackoverflow.com/a/18403796/434405, and additional ideas
  * from Scalaz.
  */
case class OptionT[M[_]: Monad, A](run: M[Option[A]]) {

  /**
    * Apply a function to the Option[A] contained by `run` using the monadM instance
    */
  private def mapOption[B](f: Option[A] => B): M[B] = run.map(f)

  /**
    * Apply the function, f, to the value contained in the Option in M.
    */
  def map[B](f: A => B): OptionT[M, B] = {
    val mapRun: M[Option[B]] = run.map((option: Option[A]) => option.map(f))

    OptionT(mapRun)
  }

  def flatMap[B](f: A => OptionT[M, B]): OptionT[M, B] = {

    /**
      * Map the Option in M to M[Option[B]] using f.
      * But f returns an OptionT so f(a).run is required to get M[Option[B]]
      */
    def mapMyOption(o: Option[A]): M[Option[B]] =
      o match {
        case None        => Option.empty[B].pure
        case Some(value) => f(value).run
      }

    val flatMapped: M[Option[B]] = run.flatMap(mapMyOption)

    OptionT(flatMapped)
  }

  /*
   * useful methods found on Option that let OptionT have an Option-like API
   * BUT all values are in M
   */

  def isDefined: M[Boolean] = mapOption(_.isDefined)

  def getOrElse(default: => A): M[A] = mapOption(_.getOrElse(default))

  // etc.
}
