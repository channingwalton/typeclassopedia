package org.typeclassopedia

import scala.Predef.implicitly

/**
  * A semigroup provides an associative operation (like + for numbers).
  * For a more formal definition, have a look at Wikipedia.
  */
trait Semigroup[T] {
  extension(a: T)
    def append(b: T): T
  
    /**
     * An alias for append
     */
    final def |+|(b: T): T = append(b)
  
    /**
     * The mathematical symbol for append
     */
    final def ⊕(b: T): T = append(b)

}
