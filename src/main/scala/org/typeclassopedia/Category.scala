package org.typeclassopedia

/**
  * A Category generalizes the notion of function composition to general morphisms.
  *
  * Recall that a type parameterised by two types can be used in infix position.
  * So the ~>[_, _] below can be used like this:  A ~> B which is the same as ~>[A, B].
  * It is convenient to use the type in infix position to convey the idea of a morphism.
  *
  * Note also that Scalaz uses =>: rather than ~> but I thought I'd use what the TMR-Issue 13 - Typeclassopedia
  * paper used.
  */
trait Category[~>[_, _]] {

  def id[A]: A ~> A

  extension[A, B, C, D](f: B ~> C)
    def compose(g: A ~> B): A ~> C
    def <<<(g: A ~> B): A ~> C = f.compose(g)
    def >>>(g: C ~> D): B ~> D = g.compose(f)
}
