package org.typeclassopedia

import scala.Boolean

/**
  * This is a blatant rip off from Scalaz.
  * There is nothing particularly tricky with this class, but its used in law checking which is useful.
  */
trait Equal[F] {

  extension[A](a1: A)
    def equal(a2: A): Boolean

  trait EqualLaw {

    def conditional(p: Boolean, q: => Boolean): Boolean = !p || q

    def commutative(f1: F, f2: F): Boolean = f1.equal(f2) == f2.equal(f1)
    def reflexive(f: F): Boolean           = f.equal(f)
    def transitive(f1: F, f2: F, f3: F): Boolean =
      conditional(f1.equal(f2) && f2.equal(f3), f1.equal(f3))
  }
  def equalLaw: EqualLaw = new EqualLaw {}
}
