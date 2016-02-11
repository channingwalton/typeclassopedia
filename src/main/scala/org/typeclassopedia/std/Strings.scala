package org.typeclassopedia.std

import java.lang.String

import org.typeclassopedia.extras.Show

trait StringShow {
  implicit val stringShow = new Show[String] {
    def show(s: String): String = s
  }
}

trait Strings extends StringShow
