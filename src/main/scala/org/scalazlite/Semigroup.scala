package org.scalazlite

trait Semigroup[T] {
  def append(a: T, b: T): T
}

trait Semigroups {
  implicit class SemigroupOps[T: Semigroup](value: T) {
    def append(b: T): T = implicitly[Semigroup[T]].append(value, b)
    final def |+|(b: T) = append(b)
  }
}