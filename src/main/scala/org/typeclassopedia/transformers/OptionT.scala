package org.typeclassopedia.transformers

import org.typeclassopedia._

case class OptionT[F[_] : Monad, A](fa: F[Option[A]]) {

  val monadF = implicitly[Monad[F]]

  def map[B](f: A ⇒ B): OptionT[F, B] = OptionT(monadF.map(fa, (_: Option[A]).map(f)))

  def flatMap[B](f: A => OptionT[F, B]): OptionT[F, B] = OptionT(monadF.flatMap(fa, (o: Option[A]) ⇒ o match {
    case None ⇒ monadF.pure(Option.empty[B])
    case Some(x) ⇒ f(x).run
  }))

  def run = fa

  val any = List(1, true, "three")
}