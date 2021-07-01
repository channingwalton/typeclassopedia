package org.typeclassopedia.std

import java.lang.String

import org.typeclassopedia.extras.Show

object Strings {

  given Show[String] with 
    extension(s : String) def show : String = s
    
}