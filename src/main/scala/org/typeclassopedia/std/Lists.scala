package org.typeclassopedia
package std

trait Lists {

  trait ListFunctor extends Functor[List] {
    def map[A, B](m: List[A], f: A ⇒ B): List[B] = m map f
  }

  trait ListPointed extends Pointed[List] {
    def point[A](a: ⇒ A) = List(a)
  }

  trait ListApplicative extends Applicative[List] {
    def <*>[A, B](ma: List[A], f: List[A ⇒ B]): List[B] = for (m ← ma; g ← f) yield g(m)
  }

  trait ListMonad extends Monad[List] with ListApplicative {
    def flatMap[A, B](ma: List[A], f: A ⇒ List[B]) = ma flatMap f
  }

  trait ListFoldable extends Foldable[List] with Semigroups {
    def foldMap[A, B: Monoid](fa: List[A])(f: A ⇒ B): B = fa.foldLeft(implicitly[Monoid[B]].zero)((b, a) ⇒ b |+| f(a))
  }

  implicit def listSemigroup[A: Semigroup]: Semigroup[List[A]] = new Semigroup[List[A]] {
    def append(a: List[A], b: List[A]) = a ::: b
  }

  trait ListTraverse extends Traversable[List] with ListFunctor with ListFoldable with Applicatives {
    def traverse[G[_] : Applicative, A, B](fa: List[A])(f: A ⇒ G[B]): G[List[B]] = {
      // a nil of the right type
      val nil: List[B] = Nil
      // an applicative instance for G
      val gapp = implicitly[Applicative[G]]
      //first map fa, a List[A] with g to get a List[G[B]]
      val lGB: List[G[B]] = fa map f

      /* now use the applicative for G to fold the list, List[G[B]], to build a G[List[B]] */
      val app = ((a: List[B], b: B) ⇒ a :+ b).curried
      lGB.foldLeft(gapp.point(nil))((acc, gb) ⇒ gb <*> gapp.map(acc, app))
    }
  }

  implicit object ListAll extends ListPointed with ListTraverse with ListMonad

}
