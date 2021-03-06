package org.typeclassopedia.transformers

import scala.{ List, Nil }
import org.typeclassopedia.Monad

/**
  * The type ListT[M[_], A] is a monad transformer that constructs a List[A] inside the monad M.
  * The original code came from http://stackoverflow.com/a/18403796/434405, and additional ideas
  * from Scalaz.
  */
case class ListT[M[_]: Monad, A](run: M[List[A]]) {

  /**
    * Apply a function to the List[A] contained by `run`
    */
  private def mapList[B](f: List[A] => B): M[B] = run.map(f)

  /**
    * Map the List in M with f
    */
  def map[B](f: A => B): ListT[M, B] = {
    val listB: M[List[B]] = mapList((l: List[A]) => l.map(f))

    ListT(listB)
  }

  def flatMap[B](f: A => ListT[M, B]): ListT[M, B] = {

    /**
      * Map the List in M to M[List[B]] using f.
      * But f returns an ListT so all results need to be concatenated and the
      * resultant ListT's run is returned
      */
    def mapMyList(l: List[A]): M[List[B]] =
      l match {
        case Nil      => Nil.pure
        case nonEmpty => nonEmpty.map(f).reduce(_ ++ _).run
      }

    ListT(run.flatMap(mapMyList))
  }

  // useful methods found on List that let ListT have a List-like API

  def ++(bs: => ListT[M, A]): ListT[M, A] =
    ListT(run.flatMap((list1: List[A]) => bs.run.map((list2: List[A]) => list1 ++ list2)))

  // etc.
}
