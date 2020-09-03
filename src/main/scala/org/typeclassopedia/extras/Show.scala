package org.typeclassopedia.extras

import scala.Predef.implicitly
import java.lang.String

trait Show[T] {
  def show(t: T): String
  
  extension(t: T)
    final def show: String = show(t)
}
