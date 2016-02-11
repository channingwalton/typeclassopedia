package org.typeclassopedia
package std

import scala.Int
import java.lang.String

trait Monoids {

  implicit object MonoidInt extends Monoid[Int] {
    def zero: Int = 0

    def append(a1: Int, a2: Int): Int = a1 + a2
  }

  implicit object MonoidString extends Monoid[String] {
    def zero: String = ""

    def append(a1: String, a2: String): String = a1 + a2
  }

}
