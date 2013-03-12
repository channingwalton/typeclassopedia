package org.typeclassopedia

trait Monoid[T] extends Semigroup[T] {
  def zero: T
}