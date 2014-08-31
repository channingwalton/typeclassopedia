package org.typeclassopedia.transformers

import org.typeclassopedia._

/**
 * The type OptionT[M[_], A] is a monad transformer that constructs an Option[A] inside the monad M.
 * The original code came from http://stackoverflow.com/a/18403796/434405, and additional ideas
 * from Scalaz.
 */
case class OptionT[M[_] : Monad, A](run: M[Option[A]]) {

  private val monadM = implicitly[Monad[M]]

  /**
   * Apply a function to the Option[A] contained by `run`
   */
  private def mapO[B](f: Option[A] ⇒ B): M[B] = monadM.map(run, f)

  def map[B](f: A ⇒ B): OptionT[M, B] = OptionT(mapO(_.map(f)))

  def flatMap[B](f: A ⇒ OptionT[M, B]): OptionT[M, B] = {
    def applyF(o: Option[A]) = o.fold(monadM.pure(Option.empty[B]))(f(_).run)
    OptionT(monadM.flatMap(run, applyF))
  }

  // useful methods found on Option that let OptionT have an Option-like API

  def isDefined: M[Boolean] = mapO(_.isDefined)

  def getOrElse(default: ⇒ A): M[A] = mapO(_.getOrElse(default))

  // etc.
}