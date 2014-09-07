package org.typeclassopedia

/**
 * It is very common to have nested types like List[Option[A]],
 * or IO[Option[A]], and it is inconvenient to work with values of these
 * types directly with nested for-comprehensions or functions.
 *
 * A monad transformer combines two monads into a single monad that shares
 * the behaviour of both.
 *
 * See the example implementations for specific examples.
 *
 * @see http://underscoreconsulting.com/blog/posts/2013/12/20/scalaz-monad-transformers.html
 * @see http://eed3si9n.com/learning-scalaz/Monad+transformers.html
 * @see http://en.wikibooks.org/wiki/Haskell/Monad_transformers
 * @see http://www.haskell.org/haskellwiki/Monad_Transformers_Explained
 */
package object transformers
