package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class FoldableSpec extends FlatSpec with MustMatchers {

  "A foldable" should "foldMap" in { List("1", "2", "3").foldMap(_.toInt) === 6 }

}