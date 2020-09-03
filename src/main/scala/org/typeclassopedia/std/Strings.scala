package org.typeclassopedia.std

import java.lang.String

import org.typeclassopedia.extras.Show

object Strings {

  trait StringShow extends Show[String] {
    override def show(s: String): String = s
  }

  given strings as StringShow

}
