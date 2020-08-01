package org.typeclassopedia

trait ArrowApply[~>[_, _]] extends Arrow[~>] {
  def app[B, C]: (B ~> C, B) ~> C
}
