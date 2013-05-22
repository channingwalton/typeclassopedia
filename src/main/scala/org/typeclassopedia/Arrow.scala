package org.typeclassopedia

/**
 * Arrow is explained in the Monad.Reader Issue 13 far better than I can.
 * In particular, read page 52 which discusses the methods below.
 * (The implementation of the methods in this trait are borrowed from scalaz.)
 */
trait Arrow[~>[_, _]] extends Category[~>] {

  def arr[B, C](f: B ⇒ C): B ~> C

  def first[B, C, D](f: B ~> C): (B, D) ~> (C, D)

  def second[A, B, C](f: A ~> B): (C, A) ~> (C, B) = {
    def swap[X, Y] = arr[(X, Y), (Y, X)] {
      case (x, y) ⇒ (y, x)
    }

    compose(swap, compose(first[A, B, C](f), swap))
  }

  def ***[B, C, B2, C2](fbc: B ~> C, fbc2: B2 ~> C2): (B, B2) ~> (C, C2) =
    compose(second[B2, C2, C](fbc2), first[B, C, B2](fbc))

  def &&&[B, C, C2](fbc: B ~> C, fbc2: B ~> C2): B ~> (C, C2) =
    compose(split(fbc, fbc2), arr((b: B) ⇒ (b, b)))

  /**
   * Borrowed from scalaz.
   */
  private def split[A, B, C, D](f: A ~> B, g: C ~> D): ((A, C) ~> (B, D)) =
    compose(second[C, D, B](g), first[A, B, C](f))

}

trait Arrows {

  implicit class ArrowOps[~>[_, _] : Arrow, B, C](arrow: B ~> C) {
    val arrowC = implicitly[Arrow[~>]]

    def first[D]: ~>[(B, D), (C, D)] = arrowC.first(arrow)

    def second[D]: (D, B) ~> (D, C) = arrowC.second(arrow)

    def ***[B2, C2](fbc2: B2 ~> C2): (B, B2) ~> (C, C2) = arrowC.***(arrow, fbc2)

    def &&&[C2](fbc2: B ~> C2): B ~> (C, C2) = arrowC.&&&(arrow, fbc2)
  }

}