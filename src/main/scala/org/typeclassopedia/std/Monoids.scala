package org.typeclassopedia.std

import org.typeclassopedia._
import scala.Int
import java.lang.String



given Monoid[Int] with 
  def zero: Int = 0

  extension(a1: Int)
    override def append(a2: Int): Int = a1 + a2



given Monoid[String] with 
  def zero: String = ""

  extension(a1: String)
    override def append(a2: String): String = a1 + a2



