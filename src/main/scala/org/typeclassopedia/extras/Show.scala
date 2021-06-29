package org.typeclassopedia.extras

import scala.Predef.implicitly
import java.lang.String

trait Show[T] :
  // no longer required see https://docs.scala-lang.org/scala3/book/ca-type-classes.html
  // def show(t: T): String
  extension(t: T) def show: String 

