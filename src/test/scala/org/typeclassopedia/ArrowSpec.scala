package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class ArrowSpec extends FlatSpec {

  val f = (x: Int) => (x * 7).toString

  "Arrow" should "first" in { assert(f.first((3, 1)) === ("21",1)) }

  it should "second" in { assert(f.second((1, 3)) === (1, "21")) }
}
