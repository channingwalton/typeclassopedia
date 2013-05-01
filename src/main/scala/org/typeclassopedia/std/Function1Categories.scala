package org.typeclassopedia.std

import org.typeclassopedia.{Category, Arrow}

trait Function1Categories {

  trait Function1Category extends Category[Function1] {
    def id[A]: (A) => A = identity

    def compose[A, B, C](f: (B) => C, g: (A) => B): (A) => C = f compose g
  }

  implicit object Function1Arrow extends Function1Category with Arrow[Function1] {
    def arr[B, C](f: B => C) = f

    def first[B, C, D](a: B => C) = (ac: (B, D)) => (a(ac._1), ac._2)
  }
}
