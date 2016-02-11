package org.typeclassopedia

import scala.{List, Option}

import org.scalatest._
import Typeclassopedia._

class CopointedSpec extends FlatSpec with Matchers {

  "A copointed functor" should "extract" in { Option(1).extract shouldEqual 1 }
  it should "extract on List" in { List(1).extract shouldEqual 1 }
  it should "extract on Blub" in { Blub(1).extract shouldEqual 1 }
}
