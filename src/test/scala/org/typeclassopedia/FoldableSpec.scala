package org.typeclassopedia

import Typeclassopedia._
import org.scalatest._

class FoldableSpec extends FlatSpec with Matchers {

  "A foldable" should "foldMap" in { List("1", "2", "3").foldMap(_.toInt) shouldEqual 6 }

}