package org.typeclassopedia.transformers

import org.typeclassopedia._

/**
 * The type OptionT[M[_], A] is a monad transformer that constructs an Option[A] inside the monad M.
 */
case class OptionT[M[_] : Monad, A](fa: M[Option[A]]) {

  val monadF = implicitly[Monad[M]]

  def map[B](f: A ⇒ B): OptionT[M, B] = OptionT(monadF.map(fa, (_: Option[A]).map(f)))

  def flatMap[B](f: A => OptionT[M, B]): OptionT[M, B] = OptionT(monadF.flatMap(fa, (o: Option[A]) ⇒ o match {
    case None ⇒ monadF.pure(Option.empty[B])
    case Some(x) ⇒ f(x).run
  }))

  def run: M[Option[A]] = fa
}