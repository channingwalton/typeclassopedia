package org.typeclassopedia

/**
  * Arrow is explained in the Monad.Reader Issue 13 far better than I can.
  * In particular, read page 52 which discusses the methods below.
  * (The implementation of the methods in this trait are borrowed from scalaz.)
  */
trait Arrow[~>[_, _]] extends Category[~>] {

  def arr[B, C](f: B => C): B ~> C

  def first[B, C, D](f: B ~> C): (B, D) ~> (C, D)

  def second[A, B, C](f: A ~> B): (C, A) ~> (C, B) = {
    def swap[X, Y]: ~>[(X, Y), (Y, X)] =
      arr[(X, Y), (Y, X)] {
        case (x, y) => (y, x)
      }

    swap.compose(first[A, B, C](f).compose(swap))
  }

  def ***[B, C, B2, C2](fbc: B ~> C, fbc2: B2 ~> C2): (B, B2) ~> (C, C2) = second[B2, C2, C](fbc2).compose(first[B, C, B2](fbc))

  def &&&[B, C, C2](fbc: B ~> C, fbc2: B ~> C2): B ~> (C, C2) = split(fbc, fbc2).compose(arr((b: B) => (b, b)))

  private def split[A, B, C, D](f: A ~> B, g: C ~> D): ((A, C) ~> (B, D)) = second[C, D, B](g).compose(first[A, B, C](f))

}

trait Arrows {

  trait ArrowOps[~>[_, _], B, C] {
    def arrowC: Arrow[~>]

    def arrow: B ~> C

    def first[D]: ~>[(B, D), (C, D)] = arrowC.first(arrow)

    def second[D]: (D, B) ~> (D, C) = arrowC.second(arrow)

    def ***[B2, C2](fbc2: B2 ~> C2): (B, B2) ~> (C, C2) = arrowC.***(arrow, fbc2)

    def &&&[C2](fbc2: B ~> C2): B ~> (C, C2) = arrowC.&&&(arrow, fbc2)
  }

  implicit class ArrowOpsInstance[~>[_, _]: Arrow, B, C](val arrow: B ~> C) extends ArrowOps[~>, B, C] {
    val arrowC: Arrow[~>] = implicitly[Arrow[~>]]
  }

}
