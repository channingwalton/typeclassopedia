package org.typeclassopedia.extras

import scala.Predef.implicitly
import java.lang.String

trait Show[T] {
  def show(t: T): String
}

trait Shows {
  implicit class ShowOps[T: Show](v: T) {
    def show: String = implicitly[Show[T]].show(v)
  }
}
