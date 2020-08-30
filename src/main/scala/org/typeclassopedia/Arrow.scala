package org.typeclassopedia

/**
  * Arrow is explained in the Monad.Reader Issue 13 far better than I can.
  * In particular, read page 52 which discusses the methods below.
  * (The implementation of the methods in this trait are borrowed from scalaz.)
  */
trait Arrow[~>[_, _]] extends Category[~>] {

  def arr[B, C](f: B => C): B ~> C
  
  private def split[A, B, C, D](f: A ~> B, g: C ~> D): ((A, C) ~> (B, D)) = g.second.compose(f.first)

  extension [A,B,C,D,B2,C2](arrow: B ~> C) {
    def first: (B, D) ~> (C, D)

    def second: (D, B) ~> (D, C) = {
      def swap[X, Y]: ~>[(X, Y), (Y, X)] =
        arr[(X, Y), (Y, X)] {
          case (x, y) => (y, x)
        }

      swap.compose(arrow.first.compose(swap))
    }

    def ***(fbc2: B2 ~> C2): (B, B2) ~> (C, C2) = fbc2.second.compose(arrow.first)

    def &&&(fbc2: B ~> C2): B ~> (C, C2) = split(arrow, fbc2).compose(arr((b: B) => (b, b)))
    
  }
}
