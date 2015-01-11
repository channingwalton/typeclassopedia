package org.typeclassopedia

trait Kleisli[M[+ _], -A, +B] {

  import Typeclassopedia._

  def runKleisli(a: A): M[B]

  final def apply(a: A): M[B] = runKleisli(a)

  final def >=>[C](k: Kleisli[M, B, C])(implicit b: Monad[M]): Kleisli[M, A, C] = kleisli((a: A) ⇒ b.flatMap(runKleisli(a), k.runKleisli(_: B)))
}

trait KleisliCategory[M[+ _]] extends Category[({type λ[α, β] = Kleisli[M, α, β]})#λ] with Kleislis {

  implicit def Monad: Monad[M]

  def id[A]: Kleisli[M, A, A] = kleisli(a ⇒ Monad.point(a))

  def compose[A, B, C](bc: Kleisli[M, B, C], ab: Kleisli[M, A, B]): Kleisli[M, A, C] = ab >=> bc
}

trait KleisliArrow[M[+ _]] extends KleisliCategory[M] with Arrow[({type λ[α, β] = Kleisli[M, α, β]})#λ] {

  def arr[A, B](f: A ⇒ B): Kleisli[M, A, B] = kleisli(a ⇒ Monad.point(f(a)))

  def first[A, B, C](f: Kleisli[M, A, B]): Kleisli[M, (A, C), (B, C)] = kleisli[M, (A, C), (B, C)] {
    case (a, c) ⇒ Monad.map(f.runKleisli(a), (b: B) ⇒ (b, c))
  }
}

trait Kleislis extends Arrows {

  implicit def kleisli[M[+ _], A, B](f: A ⇒ M[B]): Kleisli[M, A, B] = new Kleisli[M, A, B] {
    def runKleisli(a: A): M[B] = f(a)
  }

  implicit def kleisliArrow[F[+_]](implicit F0: Monad[F]): KleisliArrow[F] = new KleisliArrow[F] {
    implicit def Monad: Monad[F] = F0
  }

  implicit class KleisliArrowOps[M[+_]: Monad, A, B](val arrow: Kleisli[M, A, B]) extends ArrowOps[({type λ[α, β] = Kleisli[M, α, β]})#λ, A, B] {
    val arrowC = implicitly[KleisliArrow[M]]
  }
}
