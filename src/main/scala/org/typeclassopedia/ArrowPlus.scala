package org.typeclassopedia

trait ArrowPlus[~>[_, _]] extends ArrowZero[~>] {

  def <+>[B, C](f: B ~> C, g: B ~> C): B ~> C
}
