package org.typeclassopedia.extras

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.typeclassopedia.Blub
import org.typeclassopedia.Typeclassopedia._

class ShowSpec extends AnyFlatSpec with Matchers {

  "Show" must "produce a nice string for a string value" in {
    Blub("hi").show mustEqual "A blub of hi"
  }
}
