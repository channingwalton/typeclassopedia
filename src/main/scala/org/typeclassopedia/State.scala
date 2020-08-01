package org.typeclassopedia

/**
  * With thanks to Tony Morris: http://blog.tmorris.net/posts/memoisation-with-state-using-scala/index.html
  * I also like this explanation of the State monad: https://softwarecorner.wordpress.com/2013/08/29/scalaz-state-monad/
  */
case class State[S, A](run: S => (A, S)) {
  // 1. the map method
  def map[B](f: A => B): State[S, B] =
    State { s =>
      val (a, t) = run(s)
      (f(a), t)
    }

  // 2. the flatMap method
  def flatMap[B](f: A => State[S, B]): State[S, B] =
    State { s =>
      val (a, t) = run(s)
      f(a) run t
    }

  // Convenience function to drop the resulting state value
  def eval(s: S): A =
    run(s)._1
}

object State {
  // 3. The insert function
  def insert[S, A](a: A): State[S, A] =
    State(s => (a, s))

  // Convenience function for taking the current state to a value
  def get[S, A](f: S => A): State[S, A] =
    State(s => (f(s), s))

  // Convenience function for modifying the current state
  def mod[S](f: S => S): State[S, Unit] =
    State(s => ((), f(s)))
}
