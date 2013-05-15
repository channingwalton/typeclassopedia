package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class FoldableSpec extends FlatSpec {

  "A foldable" should "foldMap" in assert(List("1", "2", "3").foldMap(_.toInt) === 6)

}