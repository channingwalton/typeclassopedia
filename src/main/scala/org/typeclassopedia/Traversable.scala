package org.typeclassopedia

trait Traversable[M[_]] extends Functor[M] with Foldable[M] {
  /*
 * traverse ::Applicative f=>(a->fb)->ta->f(tb) 
 * sequenceA :: Applicative f⇒ t (f a) → f (t a)
 * mapM :: Monad m⇒ (a → m b) → t a → m (t b)
 * sequence ::Monadm=>t(ma)->m(ta)
 */
  def traverse[G[_] : Applicative, A, B](fa: M[A])(f: A ⇒ G[B]): G[M[B]]
}

/**
 * Implicits to help working with Traversables.
 * This is imported by Typeclassopedia so that all you need to import is Typeclassopedia._
 */
trait Traversables {

  // enrich a value of M[T] with traversable operations 
  implicit class TraversableOps[M[_] : Traversable, T](value: M[T]) {
    def traverse[G[_] : Applicative, B](f: T ⇒ G[B]): G[M[B]] = implicitly[Traversable[M]].traverse(value)(f)
  }

}