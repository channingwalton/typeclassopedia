package org.typeclassopedia

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import scala.language.implicitConversions

class CopointedSpec extends AnyFlatSpec with Matchers {

  "A copointed functor" must "extract" in { Option(1).extract mustEqual 1 }
  it must "extract on List" in { List(1).extract mustEqual 1 }
  it must "extract on Blub" in { Blub(1).extract mustEqual 1 }
}
