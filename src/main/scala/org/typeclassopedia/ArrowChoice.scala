package org.typeclassopedia

trait ArrowChoice[~>[_, _]] extends Arrow[~>] {

  def left[B, C, D](a: B ~> C): Either[B, D] ~> Either[C, D]

  def right[B, C, D](a: B ~> C): Either[D, B] ~> Either[D, C]

  def +++[B, C, B2, C2](a: B ~> C, b: B2 ~> C2): Either[B, B2] ~> Either[C, C2]

  def |||[B, C, D](a: B ~> D, b: C ~> D): Either[B, C] ~> D
}