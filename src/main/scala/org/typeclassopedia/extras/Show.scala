package org.typeclassopedia.extras

trait Show[T] {
  def show(t: T): String
}

trait Shows {
  implicit class ShowOps[T: Show](v: T) {
    def show: String = implicitly[Show[T]].show(v)
  }
}