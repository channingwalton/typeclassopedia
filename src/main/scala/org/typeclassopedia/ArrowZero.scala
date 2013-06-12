package org.typeclassopedia


trait ArrowZero[~>[_, _]] extends Arrow[~>] {
  def zeroArrow[B, C]: B ~> C
}