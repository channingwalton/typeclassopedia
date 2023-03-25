Typeclassopedia
===============

[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/channingwalton/typeclassopedia?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![Build Status](https://api.travis-ci.org/channingwalton/typeclassopedia.png)](https://travis-ci.org/channingwalton/typeclassopedia)  [![codecov](https://codecov.io/gh/channingwalton/typeclassopedia/branch/master/graph/badge.svg)](https://codecov.io/gh/channingwalton/typeclassopedia)

This project aims to be a simple implementation of the Typeclassopedia in Scala.
(If you find a simpler way to express something then please report it or send a pull request.)

Some people learn better by seeing things in code, with examples, which is why I started doing this.
The tests are intended to be examples using typical types like Option and List, but also a custom type, Blub, to show that new types can make use of the type classes defined here.

Read [The Road to the Typeclassopedia](http://channingwalton.github.io/typeclassopedia/) which I hope will be a gentle introduction to the ideas from examples rather than from theory.

How to Start
------------

I suggest you start with Functor as it is the simplest class to study, then Applicative, Monad, Semigroup and Monoid.
Then have a look at Validation which is not part of the Typeclassopedia but it's useful.

But before you do any of that, you need to understand [type classes in scala](http://dotty.epfl.ch/docs/reference/contextual/motivation.html).

The tests serve as examples of use.

Exercises
---------
There is only one exercise.

1. Check out the project
1. Delete src/main/scala/org/typeclassopedia/std
1. Delete src/test/scala/org/typeclassopedia/Blub.scala (except for the case class itself)
1. Make all the tests pass again

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
* [Applicative Programming with Effects](http://www.soi.city.ac.uk/~ross/papers/Applicative.html) McBride and Paterson’s classic paper
* [Introduction to Category Theory in Scala](http://hseeberger.wordpress.com/2010/11/25/introduction-to-category-theory-in-scala/) by Heiko Seeberger

Books
-------
* [Functional Programming in Scala](http://www.amazon.co.uk/Functional-Programming-Scala-Paul-Chiusano/dp/1617290653) by [Paul Chiusano](https://twitter.com/pchiusano) and [Runar Bjarnason](https://twitter.com/runarorama)

Talks
-----
* [Scala Typeclasses](http://www.youtube.com/watch?v=sVMES4RZF-8) presented by [Dan Rosen](https://twitter.com/mergeconflict)
* [Scala Typeclassopedia](http://www.youtube.com/watch?v=IMGCDph1fNY) presented by [John Kodumal](https://twitter.com/jkodumal)
