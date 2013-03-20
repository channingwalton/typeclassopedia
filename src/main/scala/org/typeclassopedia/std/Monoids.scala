package org.typeclassopedia
package std

trait Monoids {
  implicit object MonoidInt extends Monoid[Int] {
    def zero = 0
    def append(a1: Int, a2: Int): Int = a1 + a2
  }

  implicit object MonoidString extends Monoid[String] {
    def zero = ""
    def append(a1: String, a2: String): String = a1 + a2
  }
}