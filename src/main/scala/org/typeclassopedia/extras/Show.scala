package org.typeclassopedia.extras

import scala.Predef.implicitly
import java.lang.String

import scala.annotation.targetName

//
// Originally
// trait Show[T] {
//   def show(t: T): String
//   extension(t: T)
//     final def show: String = show(t)
// }
//

trait Show[T] :
  extension(t: T) def show : String  


