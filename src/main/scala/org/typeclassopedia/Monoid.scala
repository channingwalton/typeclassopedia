package org.typeclassopedia

/**
  * A monoid is a Semigroup with a zero value.
  */
trait Monoid[T] extends Semigroup[T] {
  def zero: T
}
