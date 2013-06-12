package org.typeclassopedia

import org.scalatest._
import Typeclassopedia._

class CopointedSpec extends FlatSpec {

  "A copointed functor" should "extract" in { assert( Option(1).extract === 1 ) }
  it should "extract on List" in { assert( List(1).extract === 1 ) }
  it should "extract on Blub" in { assert( Blub(1).extract === 1 ) }
}
