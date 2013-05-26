package org.typeclassopedia

trait ArrowChoice[~>[_, _]] extends Arrow[~>] {

  def left[B, C, D](a: B ~> C): Either[B, D] ~> Either[C, D]

  def right[B, C, D](a: B ~> C): Either[D, B] ~> Either[D, C]

  def +++[B, C, B2, C2](a: B ~> C, b: B2 ~> C2): Either[B, B2] ~> Either[C, C2]

  def |||[B, C, D](a: B ~> D, b: C ~> D): Either[B, C] ~> D
}

trait ArrowChoices {
  trait ArrowChoiceOps[~>[_, _], B, C] {
    def choice: ArrowChoice[~>]
    def arrow: B ~> C

    def left[D]: Either[B, D] ~> Either[C, D] = choice left arrow
    def right[D]: Either[D, B] ~> Either[D, C] = choice right arrow
    def +++[B2, C2](b: B2 ~> C2): Either[B, B2] ~> Either[C, C2] = choice +++ (arrow, b)
    def |||[D](b: D ~> C): Either[B, D] ~> C = choice ||| (arrow, b)
  }

  implicit class ArrowChoiceOpsInstance[~>[_, _] : ArrowChoice, B, C](val arrow: B ~> C) extends ArrowChoiceOps[~>, B, C] {
    val choice: ArrowChoice[~>] = implicitly[ArrowChoice[~>]]
  }
}