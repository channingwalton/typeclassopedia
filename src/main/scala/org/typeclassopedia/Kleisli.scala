package org.typeclassopedia

trait Kleisli[M[+ _], -A, +B] {

  import Typeclassopedia._

  def runKleisli(a: A): M[B]

  final def apply(a: A): M[B] = runKleisli(a)

  final def >=>[C](k: Kleisli[M, B, C])(implicit b: Monad[M]): Kleisli[M, A, C] = kleisli((a: A) => b.flatMap(runKleisli(a), k.runKleisli(_: B)))
}

trait KleisliArrow[M[+ _]] extends Arrow[({type λ[α, β] = Kleisli[M, α, β]})#λ] with Kleislis {

  implicit def Monad: Monad[M]

  def compose[A, B, C](bc: Kleisli[M, B, C], ab: Kleisli[M, A, B]): Kleisli[M, A, C] = ab >=> bc

  def id[A]: Kleisli[M, A, A] = kleisli(a => Monad.point(a))

  def arr[A, B](f: A => B): Kleisli[M, A, B] = kleisli(a => Monad.point(f(a)))

  def first[A, B, C](f: Kleisli[M, A, B]): Kleisli[M, (A, C), (B, C)] = kleisli[M, (A, C), (B, C)] {
    case (a, c) => Monad.map(f.runKleisli(a), (b: B) => (b, c))
  }
}

trait Kleislis {
  implicit def kleisli[M[+ _], A, B](f: A => M[B]): Kleisli[M, A, B] = new Kleisli[M, A, B] {
    def runKleisli(a: A) = f(a)
  }

  def liftKleisliArrow[M[+_]: Monad] = new KleisliArrow[M] {
    def Monad: Monad[M] = implicitly[Monad[M]]
  }

}