package org.typeclassopedia.extras

import org.typeclassopedia.Applicative
import org.typeclassopedia.Semigroup

/**
 * Validation is not part of the typeclassopedia but I added it because … I could.
 */
sealed trait Validation[+E, +A]

final case class Success[E, A](a: A) extends Validation[E, A]

final case class Failure[E, A](e: E) extends Validation[E, A]

trait Validations {

  /**
   * Provide an Applicative instance for a Validation.
   */
  implicit def ValidationApplicative[L: Semigroup]: Applicative[({type l[a] = Validation[L, a]})#l] = new Applicative[({type l[a] = Validation[L, a]})#l] {
    def <*>[A, B](m: Validation[L, A], f: Validation[L, A ⇒ B]): Validation[L, B] = (m, f) match {
      case (Success(a), Success(fn)) ⇒ Success[L, B](fn(a))
      case (Failure(e), Success(_)) ⇒ Failure[L, B](e)
      case (Success(_), Failure(e)) ⇒ Failure[L, B](e)

      // This is the sneaky bit, here Failures are appended using a Semigroup instance for the type L, the failure type.
      case (Failure(e1), Failure(e2)) ⇒ Failure[L, B](implicitly[Semigroup[L]].append(e2, e1))
    }

    def map[A, B](m: Validation[L, A], f: A ⇒ B): Validation[L, B] = m match {
      case Success(a) ⇒ Success[L, B](f(a))
      case Failure(e) ⇒ Failure[L, B](e)
    }

    def point[A](a: ⇒ A): Validation[L, A] = Success[L, A](a)
  }
}