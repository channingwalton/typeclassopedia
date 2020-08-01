The Road to the Typeclassopedia
===============================

People learn in many ways. For computer science and programming, some enjoy starting from the math, the theory, and proofs.
From there they find their way to practical concerns in everyday development.
They think in abstract algebra and category theory, encoding their solutions in their chosen language.

That's awesome.

Other people learn differently, by example, with problems solved with patterns and techniques that later they learn have names and ideas rooted in mathematics.

There are a lot of excellent books, papers and blogs that follow the first path, usually because the authors
tend to be the kind of people that are happy to learn from the math and theory, applying it later.

This book follows the second path, describing a set of ideas collectively called the Typeclassopedia, by starting from a problem and developing a solution.

The Path
========

Option
------

Here is some code that returns a value:

``` scala
  trait Blub {

    /**
    * @return a Foo or null
    */
    def foo(): Foo
  }
```

This is a common pattern, the method returns a Foo instance or null if the method cannot do its job.
Anyone calling this method needs to know that the result might be null which is extremely error prone and completely insane.
We can do better than that by returning a value that represents that fact that the method might not be able to return a result, an optional value.

Instead of the insidious null, we can represent this optional value with a type called *Option*. A type that represents a value that may or may not exist.

Here it is:

``` scala
  sealed trait Option[+A]

  // we have a value
  case class Some[A](v: A) extends Option[A]

  // no value
  case object None extends Option[Nothing]
```

Notice that `None extends Option[Nothing]`, `Nothing` is the bottom type which extends all other types. Because `Option` is covariant, `Option[Nothing]` extends all other `Option` types.

So now Blub looks like:

``` scala
  trait Blub {
    def foo(): Option[Foo]
  }
```

Two things have happened:

1.  the method now returns a type that represents the optional nature of the returned value, no more `null`!
2.  the comment has gone. It is not needed anymore because the method signature is sufficient.

Now we have another problem, how do we work with the result? We certainly don't want to explicitly detect whether
the result is a *Some* or *None* everytime by pattern matching or other means.

Instead, we can add a method to Option that enables a function to be applied to the value. Traditionally, that method is called *map*.

``` scala
  trait Option[+A] {
    def map[B](f: A => B): Option[B] =
      this match {
        case Some(a) => Some(f(a))
        case None => None
      }
  }
```

If the Option is a Some, then the functon can be applied to the value, and a new Some returned with the result.
If the Option is a None, then map can only return None since there is no value to apply the function to.

So we can now work with values in options not by pattern matching but by mapping with a function.

List
----

Lists are important data structures and its self-evident why they are needed.

Scala's standard library comes with a List so we won't go into writing one (hint: write one yourself as an exercise) but there are a couple of things to point out.

1.  Like Option, List requires a type parameter. eg List\[Int\] for a list of integers
2.  It has a map method like Option

List's map method is roughly like this:

``` scala
  def map[U](f: T => U): List[U]
```

Like Option, the map method takes a function, f, and returns a new List.

For comprehensions
------------------

In scala, if an object has a `map` method like Option and List do, you can use a *for comprehension*, syntactic sugar that compiles to map:

``` scala
  val x: Option[String] = ...

  for {
    v <- x
  } yield v.length

  // is the same as
  x.map(v => v.length)
```

But for comprehensions can do a lot more as we will see later.

The First Abstraction
---------------------

Looking at Option and List we find that both *map* methods are very similar:

1.  They both take a function that transforms the element(s) of List and Option
2.  They both obey some obvious *laws*
    1.  Identity: If the function given is the *identity* function, then the returned Option or List is the same as the original
    2.  Composition: If map is first passed `f: A => B`, and then `g: B => C`, its the same as passing a composite function: `f andThen g`, or `g compose f`.

So the question is, can we come up with something more general, and if we can, how do we express it and use it?

Unsurprisingly the answer is yes because in Scala, and other languages like it, you can *abstract over type constructors*.

Lets look at the map method again but recast it slightly differently:

``` scala
  def map[A, B](o: Option[A], f: A => B): Option[B]
```

So instead of a map method on Option, we can put this method *somewhere* and call it. Its more cumbersome but stay with me. Lets do the same for List:

``` scala
  def map[A, B](o: List[A], f: A => B): List[B]
```

These method signatures are practically identical and vary only in the type argument.
This method can be defined in terms of any type that takes a single parameter, a type constructor, like this:

``` scala
  trait MappingThing[M[_]] {
    def map[A, B](m: M[A], f: A => B): M[B]
  }
```

We will come to what its called later, the important point is that the trait has a *map* method for any M that is a type constructor, hence the type parameter is M\[\_\].

The implementations for List and Option are straight forward:

``` scala
  object ListMappingThing extends MappingThing[List] {
    def map[A, B](m: List[A], f: A => B): List[B] = ??? // map a list
  }

  object OptionMappingThing extends MappingThing[Option] {
    def map[A, B](m: Option[A], f: A => B): Option[B] = ??? // map an option
  }
```

Note that List and Map do not need their map methods anymore, we can use these two class instead, but how?

It would be very inconvenient to have to call `ListMappingThing.map` or `OptionMappingThing.map` directly,
and actually totally useless when we've written code that doesn't know if we have a List or Option, code that has abstracted over the type constructor itself.

The solution is to use the *typeclass* pattern. Code that needs a MappingThing can ask the compiler to provide it, but including an implicit parameter list with a `MappingThing[M]`:

``` scala
  def launch[A, M[_]](m: M[A])(implicit mappingThing: MappingThing[M]): Result = {
    val v: M[B] =  mappingThing.map(m, myFunkyFunction)

    // do things with v and return a result
  }
```

But to make the Option and List MappingThings available for the compiler to find we need to make their instances implicit:

``` scala
  object AllTheMappingThings {
    implicit object ListMappingThing extends MappingThing[List] {
      def map[A, B](m: List[A], f: A => B): List[B] = ??? // map a list
    }

    implicit object OptionMappingThing extends MappingThing[Option] {
      def map[A, B](m: Option[A], f: A => B): Option[B] = ??? // map an option
    }
  }
```

I have put the instances inside another object, but the right place for it is in the List and Option companion objects.

### Summary

We have learnt a new way to decouple behaviour from data types using typeclasses that has numerous advantages:

1.  The concept of mapping, the map method, has been extracted to its own type. The operation now has a life of its own independent of the specific types that support it.
2.  Code can now be written more generally, and therefore be more generally useful, in terms of the MappingThing typeclass, not concrete instances.
    We don't need to write the *launch* method twice, once for Option and once for List.
3.  Now that we have a typeclass, anyone can write more of them for whatever types they want.
    Suddenly code written in terms of MappingThing can be used in all kinds of different contexts.

### The Name

What we've just invented is some called a *functor*. Mathematically, a functor is a mapping between so-called categories, but we aren't going to go there.

Squash it
---------

We have a small problem with our map method, it can return anything at all. Why is that a problem?

``` scala
  // a function that returns an option
  def sqrt(x: Double): Option[Double] = if (x >=0) Some(math.sqrt(x)) else None

  // a value in an option

  val x: Option[Int] = ???

  val y: Option[Option[Int]] = x.map(v => sqrt(v))
```

y has ended up as an Option of an Option of Int which is annoying. So to handle this special case we are going to introduce a new method called *flatMap*.
In Option it looks like this:

``` scala
  sealed trait Option[A] {
    def map[B](f: A => B): Option[B] = ???

    def flatMap[B](f: A => Option[B]): Option[B] =
      this match {
        case None => None
        case Some(x) => f(x)
      }
  }
```

List has a similar method.

This method turns out to be very useful. Lets assume we have two Options and we want to work with the values of both in some way, flatMap will be useful:

``` scala
  val x: Option[Int] = ???
  val y: Option[Int] = ???

  val sum: Option[Int] = x.flatMap(xv => y.map(yv => xv + yv))
```

This doesn't look so nice but fortunately scala's for comprehension deals with this by offering syntactic sugar for both map and flatMap. The above is identical to:

``` scala
  val sum: Option[Int] =
    for {
      xv <- x
      yv <- y
    } yield xv + yv
```

Much better.

List has a similar method so that we can work with multiple lists like this:

``` scala
  val x: List[Int] = ???
  val y: List[Int] = ???

  val sums: List[Int] =
     for {
      xv <- x
      yv <- y
    } yield xv + yv
```

The Second Abstraction
----------------------

Looking at Option and List we find that both *flatMap* methods are very similar, and as with the map method we can recast it a little:

Option:

``` scala
  def flatMap[A, B](o: Option[A], f: A => Option[B]): Option[B]
```

List:

``` scala
  def flatMap[A, B](o: List[A], f: A => List[B]): List[B]
```

These methods are practically identical, what we can do is define this method in terms of any type that takes a single parameter, a type constructor, like this:

``` scala
  trait FlatMappingThing[M[_]] {
    def flatMap[A, B](m: M[A], f: A => M[B]): M[B]
  }
```

The implementations for List and Option are:

``` scala
  object AllTheFlatMappingThings {
    implicit object ListFlatMappingThing extends FlatMappingThing[List] {
      def flatMap[A, B](m: List[A], f: A => List[B]): List[B] = ???
    }

    implicit object OptionFlatMappingThing extends FlatMappingThing[Option] {
      def flatMap[A, B](m: Option[A], f: A => Option[B]): Option[B] = ???
    }
  }
```

And using this code with typeclasses:

``` scala
  def launch[A, M[_]](m: M[A])(implicit flatMappingThing: FlatMappingThing[M]): Result = {
    val mapped: M[B] =  flatMappingThing.flatMap(m, myFunkyFunction)
    // return a result
  }
```

So this is very similar to the functor case.

### Summary

We have applied the same pattern as the functor above, but this time for the flatMap method.
This enables us to cope with multple values of Options, Lists or any other kind of type constructor, or functions that return Options, Lists, etc.

### The Name

Stand back ... it is a Monad!

There is a little more to a monad than just a flatMap method, it needs to obey some laws too which we will skip,
but if you're interested search [Google](https://www.google.com/search?q=scala+monad+laws) for Scala Monad Laws.

Syntax
------

The abstractions above are great, but because we've moved the map and flatMap methods to typeclasses,
scala for-comprehensions won't work since the map and flatMap method are no longer on the objects you are working with.
In the specific case of Option and List they do have those methods because they are part of the Scala library and thats
what the original authors did. But if you had a new type for which you'd defined typeclass instances, then that new type won't have map and flatMap.

The solution is to provide some syntax for any type that has a Functor or Monad typeclass.

``` scala
  implicit class FunctorSyntax[F[_]: Functor, A](v: F[A]) {
    def map[B](f: A => B): F[B] = implicitly[Functor[F]].map(v, f)
  }
```

``` scala
  implicit class MonadSyntax[M[_]: Monad, A](v: M[A]) {
    def flatMap[B](f: A => M[B]): M[B] = implicitly[Monad[F]].flatMap(v, f)
  }
```

… to be continued.

Basic Theory
============

Types, Kinds and Type Constructors
----------------------------------

A *variable* has a *type*. For example, `x: Int` means the variable `x` has the type `Int`.

A *proper type*, also known as an *inhabitated* type, is a type that can have values. The type Int is a proper type because it has values like 0 and 1.
`List[Int]` is also an inhabited, or proper type, that is inhabited by values like `1 :: 2 :: Nil`, instances of a list.

A *type constructor* is something that takes a type and produces a type. Examples are anything with type parameters like List or Option.
Type constructors are not inhabited, it is not possible to have values of List or Option, only List\[X\] or Option\[Y\].

In type theory it is useful to denote different *kinds* of types so that we can talk more generally about types, type constructors, etc.

### Extra Theory

Types are denoted with an asterisk: *Int* is of type \*

A type constructor taking a single type is denoted like this: `*
-> *`, meaning that given a type, denoted by the first \*, it will produce a new proper type, \*. For example, A List given an Int will produce List\[Int\].

Something like Either\[A, B\], which takes two type parameters, is denoted like this: `*-> * -> *` because it takes two proper types and produces a type.
`Either[Int, String]` is a proper type contructed with Either and two proper types, Int and String.

*Kinds* are a way of describing similar types at an abstract level. `*`, `* ->
*`, `* -> * -> *`, are *kinds*. Kinds are useful when working more abstractly with types, both proper and improper (inhabited or uninhabited).

Further reading [Wikipedia](https://en.wikipedia.org/wiki/Kind_(type_theory)) [Types of a Higher Kind](http://blogs.atlassian.com/2013/09/scala-types-of-a-higher-kind/) [Stack Exchange](http://programmers.stackexchange.com/a/255928/18311)

Typeclasses
-----------

Typeclasses are an extremely common pattern used extensively in libraries like the [Typelevel](http://typelevel.org/) libraries. Martin Odersky et al describe typeclasses as follows:

> Type classes are useful to solve several fundamental challenges in software engineering and programming languages. In particular type classes support retroactive extension: the ability to extend existing software modules with new functionality without needing to touch or re-compile the original source.

Read more here: [Type Classes as Objects and Implicits](http://ropas.snu.ac.kr/~bruno/papers/TypeClasses.pdf)

In Scala, the typeclass pattern consists of several parts:

-   a trait with one or more type parameters that defines some behaviour
-   an optional companion object of the trait with *implicit* instances of the trait for common types like Strings or Options, and other types present in the standard library.

For example, a trait with a show method to transform a T into a String

``` scala
  trait Show[T] {
    def show(t: T): String
  }

  // A companion object with some useful default instance of the typeclass
  object Show {

    implicit object StringShow extends Show[String] {
      def show(s: String): String = s.toString
    }

    implicit object IntShow extends Show[Int] {
      def show(t: Int) = t.toString
    }

    // This show requires a Show[T] so its a method
    implicit def listShow[T](implicit tShow: Show[T]):  Show[List[T]]  = new Show[List[T]] {

      def show(l: List[T]): String = {
        val list = l.map(v => tShow.show(v)).mkString(",")
        s"List($list)"
      }

    }
   }

  // Example of use
  def foo[T](v: T)(implicit show: Show[T]) = show.show(v)

  println(foo("hi"))

  println(foo(123))

  println(foo(List(1,2,3,4,5)))
```

As an exercise, implement Show\[Option\[T\]\] and Show\[Either\[A, B\]\]

Common types and what they represent
====================================

It is not uncommon to read blogs or other articles in which the follow statements are made:

-   Lists represent indeterminism
-   Either is a disjoint union
-   etc.

What do they mean?

Option (or Maybe)
-----------------

Option represents an optional value, or sometimes, success and failure.

It has two constructors: None and Some\[T\], where Some\[T\] contains a value of type T.

Option is of kind `* -> *`

List
----

Lists represent lists of things, but they are often referred to as representing *indeterminism*.
A function returning a list can return any number of values including nothing (the empty list), hence the list is being used to represent an indeterministic result.

Lists are *recursive* data structures because they are typically defined in terms of themselves: a list is either the empty list, Nil, or a single element followed by a list.

List is of kind `* -> *`

Either
------

Either is of kind `* -> * -> *`

Validation
----------

Identity
--------

Intuitively it simply wraps a value, it does not embody anything meaningful but is useful in some contexts beyond the scope of this document.

Identity is of kind `* -> *`

Further Reading
---------------

* [Typeclassopedia](http://www.cs.tufts.edu/comp/150FP/archive/brent-yorgey/tc.pdf)
* [Modeling Uncertain Data using Monads and an Application to the Sequence Alignment Problem](https://pp.ipd.kit.edu/uploads/publikationen/kuhnle13bachelorarbeit.pdf) 
* [Functors, Applicatives, And Monads In Pictures](http://adit.io/posts/2013-04-17-functors,_applicatives,_and_monads_in_pictures.html)
