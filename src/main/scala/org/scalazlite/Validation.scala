package org.scalazlite

sealed trait Validation[+E, +A]

final case class Success[E, A](a: A) extends Validation[E, A]
final case class Failure[E, A](e: E) extends Validation[E, A]
