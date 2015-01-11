package org.typeclassopedia

trait Copointed[F[_]] extends Functor[F] {

  def extract[A](f: F[A]): A

  final def copoint[A](f: F[A]): A = extract(f)

  final def copure[A](f: F[A]): A = extract(f)
}

trait CoPoints {

  /**
   * Wrap a simple type in a Pointed
   */
  implicit class CoPointedValueOps[F[_]: Copointed, T](value: F[T]) {
    def extract: T = implicitly[Copointed[F]].extract(value)
    def copoint: T = extract
    def copure: T = extract
  }

}