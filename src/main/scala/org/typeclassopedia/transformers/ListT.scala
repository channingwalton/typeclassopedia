package org.typeclassopedia.transformers

import org.typeclassopedia._

/**
 * The type ListT[M[_], A] is a monad transformer that constructs a List[A] inside the monad M.
 * The original code came from http://stackoverflow.com/a/18403796/434405, and additional ideas
 * from Scalaz.
 */
case class ListT[M[_] : Monad, A](run: M[List[A]]) {

  private val monadM = implicitly[Monad[M]]

  /**
   * Apply a function to the List[A] contained by `run`
   */
  private def mapO[B](f: List[A] ⇒ B): M[B] = monadM.map(run, f)

  def map[B](f: A ⇒ B): ListT[M, B] = ListT(mapO(_.map(f)))

  def flatMap[B](f: A ⇒ ListT[M, B]): ListT[M, B] = {
    def applyF(o: List[A]): M[List[B]] = o match {
      case Nil ⇒ monadM.pure(Nil)
      case nonEmpty ⇒ nonEmpty.map(f).reduce(_ ++ _).run
    }
    ListT(monadM.flatMap(run, applyF))
  }

  // useful methods found on List that let ListT have a List-like API

  def ++(bs: => ListT[M, A]): ListT[M, A] = ListT(monadM.flatMap(run, (list1: List[A]) ⇒
    monadM.map(bs.run, (list2: List[A]) ⇒
      list1 ++ list2
    )
  ))

  // etc.
}
