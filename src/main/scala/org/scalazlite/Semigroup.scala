package org.scalazlite

/**
 * A semigroup provides an associative operation (like + for numbers).
 * For a more formal definition, have a look at Wikipedia.
 */
trait Semigroup[T] {
  def append(a: T, b: T): T
}

/**
 * Implicits to help working with semigroups.
 */
trait Semigroups {
  /**
   * Lift an instance into a Semigroup.
   */
  implicit class SemigroupOps[T: Semigroup](value: T) {
    def append(b: T): T = implicitly[Semigroup[T]].append(value, b)

    /**
     * An alias for append
     */
    final def |+|(b: T) = append(b)
  }
}