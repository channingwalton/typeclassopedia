Typeclassopedia
===============

[![Build Status](https://api.travis-ci.org/channingwalton/typeclassopedia.png)](https://travis-ci.org/channingwalton/typeclassopedia)

This project aims to be the simplest implementation of the Typeclassopedia in Scala.
(If you find a simpler way to express something then please report it or send a pull request.)

Some people learn better by seeing things in code, with examples, which is why I started doing this.
The tests are intended to be examples using typical types like Option and List, but also a custom type, Blub, to show that new types can make use of the type classes defined here.

How to Start
------------

I suggest you start with Functor as it is the simplest class to study, then Applicative, Monad and Semigroup. 
But before you do any of that, you need to understand [typeclasses in scala](http://www.casualmiracles.com/2012/05/03/a-small-example-of-the-typeclass-pattern-in-scala/).

The tests serve as examples of use.

Further Reading
-------------------
* This project contains a copy of *The Monad.Reader Issue 13*  containing Brent Yorgey's *The Typeclassopedia* which provides descriptions of all the types defined here.
* [Type Basics](http://twitter.github.com/scala_school/type-basics.html)
* [Advanced Types](http://twitter.github.com/scala_school/advanced-types.html) - particularly the section on *Higher-kinded types & ad-hoc polymorphism*
* [Functors, Monads, Applicatives – can be so simple](http://thedet.wordpress.com/2012/04/28/functors-monads-applicatives-can-be-so-simple/) by [Dirk Detering](https://twitter.com/developmind)
* [The Essence of the Iterator Pattern](http://etorreborre.blogspot.com.au/2011/06/essence-of-iterator-pattern.html) by [Eric Torrebore](https://twitter.com/etorreborre)
* [Haskell Typeclassopedia](http://www.haskell.org/haskellwiki/Typeclassopedia)
* [Typeclassopedia presentation](http://typeclassopedia.bitbucket.org) (use arrow keys)
* [Learning Scalaz](http://eed3si9n.com/learning-scalaz-day1) by [Eugene Yokota](https://twitter.com/eed3si9n)
* [Small (scala) examples](http://www.casualmiracles.com/category/small-examples/) by [me](https://twitter.com/channingwalton)
* [Applicative Programming with Effects](http://www.soi.city.ac.uk/~ross/papers/Applicative.html) McBride and Paterson’s classic paper

Books
-------
* [Functional Programming in Scala](http://www.amazon.co.uk/Functional-Programming-Scala-Paul-Chiusano/dp/1617290653) by [Paul Chiusano](https://twitter.com/pchiusano) and [Runar Bjarnason](https://twitter.com/runarorama)

Talks
-----
* [Scala Typeclasses](http://www.youtube.com/watch?v=sVMES4RZF-8) presented by [Dan Rosen](https://twitter.com/mergeconflict)
* [Scala Typeclassopedia](http://www.youtube.com/watch?v=IMGCDph1fNY) presented by [John Kodumal](https://twitter.com/jkodumal)
