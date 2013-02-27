package org.scalazlite

trait Applicative[M[_]] {
  def <*>[A, B](m: M[A], f: M[A ⇒ B]): M[B]
}

trait StandardApplicatives {
  implicit object OptionApplicative extends Applicative[Option] {
    def <*>[A, B](ma: Option[A], f: Option[A ⇒ B]): Option[B] = for (m ← ma; g ← f) yield g(m)
  }
}

trait Applicatives extends StandardApplicatives {
  implicit class ApplicativeOps[M[_]: Applicative, T](value: M[T]) {
    def <*>[B](f: M[T ⇒ B]): M[B] = implicitly[Applicative[M]] <*> (value, f)
  }
}