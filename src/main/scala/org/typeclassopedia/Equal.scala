package org.typeclassopedia

/**
  * This is a blatant rip off from Scalaz.
  * There is nothing particularly tricky with this class, but its used in law checking which is useful.
  */
trait Equal[F]  {
 
  def equal(a1: F, a2: F): Boolean

  trait EqualLaw {

    def conditional(p: Boolean, q: => Boolean) = !p || q

    def commutative(f1: F, f2: F): Boolean = equal(f1, f2) == equal(f2, f1)
    def reflexive(f: F): Boolean = equal(f, f)
    def transitive(f1: F, f2: F, f3: F): Boolean = {
      conditional(equal(f1, f2) && equal(f2, f3), equal(f1, f3))
    }
  }
  def equalLaw = new EqualLaw {}
}
