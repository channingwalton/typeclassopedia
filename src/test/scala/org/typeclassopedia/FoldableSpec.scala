package org.typeclassopedia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import scala.language.implicitConversions

class FoldableSpec extends AnyFlatSpec with Matchers {

  "A foldable" must "foldMap" in { List("1", "2", "3").foldMap(_.toInt) mustEqual 6 }

}
