package org.typeclassopedia

/**
 * A Category generalizes the notion of function composition to general morphisms.
 *
 * Recall that a type parameterised by two types can be used in infix notation.
 * So the ~>[_, _] below can be used like this:  A ~> B which is the same as ~>[A, B].
 * It is convenient to use the type in infix position to convey the idea of a morphism.
 *
 * Note also that Scalaz uses =>: rather than ~> but I thought I'd use what the TMR-Issue 13 - Typeclassopedia
 * paper used.
 */
trait Category[~>[_, _]] {

  def id[A]: A ~> A

  def compose[A, B, C](f: B ~> C, g: A ~> B): A ~> C
}

trait Categories {
  implicit class CategoryOps[~>[_, _]: Category, B, C](c: B ~> C) {
    def compose[A](g: A ~> B): A ~> C = implicitly[Category[~>]].compose(c, g)
    def <<<[A](g: A ~> B): A ~> C = compose(g)
  }
}
