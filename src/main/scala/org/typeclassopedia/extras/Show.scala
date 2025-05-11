package org.typeclassopedia.extras

import scala.Predef.implicitly
import java.lang.String

trait Show[T]:
  def show(t: T): String

object Show:
  extension[T](t: T)(using showT:Show[T])
    final def show: String = showT.show(t)
