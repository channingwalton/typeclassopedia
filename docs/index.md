# The Road to the Typeclassopedia in Scala 3

People learn in many ways. For computer science and programming, some enjoy starting from the maths, the theory, and proofs.
From there they find their way to practical concerns in everyday development.
They think in abstract algebra and category theory, encoding their solutions in their chosen language.

That's awesome.

Other people (most) learn differently, by example, with problems solved with patterns and techniques that they later
discover have names and ideas rooted in mathematics.

There are a lot of excellent books, papers and blogs that follow the theoretical path, usually because the authors
tend to be the kind of people that are happy to learn from the maths and theory, applying it later.

This document follows the second path, describing a set of ideas collectively called the Typeclassopedia,
by starting from a problem and developing a solution.

## Option

Here is some code that returns a value:

``` scala
  trait Blub {

    /**
    * @return a Foo or null
    */
    def foo(): Foo
  }
```

This is a common pattern, the function returns a Foo instance or `null` if the function cannot do its job.
Anyone calling this function needs to know that the result might be null which is extremely error prone and completely insane.
Nulls have a way of migrating around a codebase and blow up code in unexpected ways far away from their origin.

We can do better by returning a value that represents the possibility that the function might not return a result, an _optional_ value.
Or use it in a data model to represent an optional field.

The type is called `Option`, (or `Maybe` or `Optional`), and here is a trivial encoding:

``` scala
  sealed trait Option[+A]

  // we have a value
  case class Some[A](v: A) extends Option[A]

  // no value
  case object None extends Option[Nothing]
```

Notice that `None extends Option[Nothing]` which is the bottom type which extends all other types.
Because `Option` is covariant, `Option[Nothing]` extends all other `Option` types.

Using `Option` in `Blub` results in:

``` scala
  trait Blub {
    def foo(): Option[Foo]
  }
```

Three things have happened:

1.  the function now returns a type that represents the optional nature of the returned value, no more `null`!
2.  the comment has gone, it is not needed because the function signature is sufficient.
3.  every caller of the function now knows it may not return a value.

But how do we work with an `Option`? We can pattern match but that becomes cumbersome pretty quickly.

Instead, we can add a function to `Option` that applies a given function to the value.
Traditionally, that function is called `map`.

``` scala
  trait Option[+A] {
    def map[B](f: A => B): Option[B] =
      this match {
        case Some(a) => Some(f(a))
        case None => None
      }
  }
```

If the `Option` is a `Some`, then the function can be applied to the value, and a new `Some` is returned with the result.
If the `Option` is a `None`, then map can only return `None` since there is no value to apply the function to.

So we can now work with values in options by mapping an `Option` with a function.

## List

Scala's standard library comes with a `List` so we won't go into writing one
(challenge: write one yourself as an exercise) but there are a couple of things to point out.

1.  Like `Option`, `List` requires a type parameter, eg. `List[Int]` for a list of integers
2.  It has a map function like `Option`

List's map function is:

``` scala
  def map[U](f: T => U): List[U]
```

Like `Option`, the `map` function takes a function, f, and returns a new `List`.

## For comprehensions

In Scala, if a type has a `map` function like `Option` and `List` do, it can be used in a *for comprehension*,
syntactic sugar that compiles to map:

``` scala
  val x: Option[String] = ...

  for {
    v <- x
  } yield v.length

  // is the same as
  x.map(v => v.length)
```

There is more required to really use Options and Lists in for comprehensions which we will see later.

## The First Abstraction

`Option` and `List` *map* functions are very similar:

1.  They both take a function that transforms the element(s) of `List` and Option
2.  They both obey some obvious *laws*
    1.  Identity: If the function given is the *identity* function, then the returned `Option` or `List` is the same as the original
    2.  Composition: If map is first passed `f: A => B`, and then `g: B => C`, the result is equivalent to passing a composite
        function: `f andThen g`, or `g compose f`.

So the question is, can we abstract this concept into something more general?

Unsurprisingly the answer is yes, and that abstraction is called a **Functor**. Here it is:

```scala
trait Functor[F[_]] {
  def map[A, B](f: A => B): F[B]
}
```

This trait simply says that a Functor for some [Higher-Kinded](https://www.baeldung.com/scala/higher-kinded-types) type `F[_]` must provide a map function, just as the map functions in `Option` and `List` do.

However, instead of making types implement this trait, a much more flexible mechanism is to decouple types from their Functor instances, allowing any type with type constructors like `Option` and `List` (taking a single type parameter) to have a Functor instance.

To do this we use the power of **typeclasses** in Scala, which looks like this in Scala 3:

```scala
trait Functor[F[_]] {
  extension [A, B](x: F[A])
    def map(f: A => B): F[B]
}
```

Its very similar to the original trait but instead declares *map* as an extension function for any type `F[A]`. The implementations for `List` and `Option` are straight forward:

``` scala
  given Functor[Option] {
    extension [A, B](m: Option[A])
      override def map(f: A => B): Option[B] = m.map(f)
  }

  given Functor[List] {
    extension [A, B](m: List[A])
      override def map(f: A => B): List[B] = m.map(f)
  }
```

Now we can use them in functions that do not need to know the explicit type they are working with, just that there is a Functor available for it:

``` scala
def launch[A, M[_]: Functor](m: M[Missile]): Result = {
  val v: M[B] =  m.map(_.launch)
  // …
}

// launch everything
launch(listOfMissiles)

// launch a missile if we have one
launch(optionOfMissile)
```

## Summary

We have learnt a new way to decouple behaviour from data types using typeclasses that has numerous advantages:

1.  The concept of mapping, the map function, has been extracted to its own type. The operation now has a life of its own independent of the specific types that support it.
2.  Code can now be written more generically, and therefore be more generally useful and avoiding duplication, in terms of the Functor typeclass, not concrete instances.
    We don't need to write the *launch* function twice, once for `Option` and once for `List`.
3.  Now that we have a typeclass, anyone can write more of them for whatever types they want.
    Suddenly code written in terms of Functor can be used in all kinds of different contexts.


Learn more about Scala 3 typeclasses and all the syntax used above [here](https://dotty.epfl.ch/docs/reference/contextual/motivation.html).

## The M word

We have a small problem with our map function, it can return anything at all. Why is that a problem?

``` scala
  // a function that returns an option
  def sqrt(x: Double): Option[Double] = if x >=0  then Some(math.sqrt(x)) else None

  // a value in an option
  val x: Option[Int] = ???

  val y: Option[Option[Int]] = x.map(v => sqrt(v))
```

`y` has ended up as an `Option` of an `Option` of `Int` which is annoying. So to handle this case we are going to introduce a new function called `flatMap`.
In `Option` it looks like this:

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

`List` has a similar function.

This function turns out to be very useful. Lets assume we have two Options and we want to work with the values of both in some way, `flatMap` will be useful:

``` scala
  val x: Option[Int] = ???
  val y: Option[Int] = ???

  val sum: Option[Int] = x.flatMap(xv => y.map(yv => xv + yv))
```

This doesn't look so nice but fortunately Scala's for comprehension deals with this by offering syntactic sugar for both `map` and `flatMap`. The above is identical to:

``` scala
  val sum: Option[Int] =
    for {
      xv <- x
      yv <- y
    } yield xv + yv
```

Much better.

List has a similar function so that we can work with multiple lists like this:

``` scala
  val x: List[Int] = ???
  val y: List[Int] = ???

  val sums: List[Int] =
     for {
      xv <- x
      yv <- y
    } yield xv + yv
```

## The Second Abstraction - Monad

Looking at `Option` and `List` we see that both `flatMap` functions are very similar, and as with `map` there is a well-known typeclass for `flatMap`:

```scala
trait Monad[F[_]] {
  def flatMap[A, B](f: A => F[B]): F[B]
}

given Monad[Option] {
  extension [A, B](m: Option[A])
    override def flatMap(f: A => Option[B]): Option[B] = m.flatMap(f)
}

given Monad[List] {
  extension [A, B](m: List[A])
    override def flatMap(f: A => List[B]): List[B] = m.flatMap(f)
}
```

## Summary

We have applied the same pattern as the functor above, but this time for the flatMap function.
This enables us to cope with multple values of Options, Lists or any other kind of type constructor, or functions that return Options, Lists, etc.

Aside: Monad's are also Functors so:

```scala
trait Monad[F[_]] extends Functor[F] {
  def flatMap[A, B](f: A => F[B]): F[B]
}
```

## The answer is always traverse

Let's say we have a list of users and we want to fetch details for each one. The `userDetails` function returns an `IO` (kind of like Future but its a Monad):

``` scala
def userDetails(user: User): IO[UserDetails] = ...
```

If we try to use `map`, we get a problem:

``` scala
val result: List[IO[UserDetails]] = users.map(userDetails)
```

We end up with a `List[IO[UserDetails]]` instead of what we really want: a single IO operation that fetches all user details (`IO[List[UserDetails]]`).

What we need is a function that can "traverse" the list, applying the function to each element, but also "flip" the types so we get `IO[List[UserDetails]]` instead of `List[IO[UserDetails]]`.

This is what `traverse` does:

``` scala
trait Traverse[T[_]] {

  extension[F[_], A, B](fa: T[A])
    def traverse(f: A => F[B]): F[T[B]]
```

Note that this function signature isn't quite right. It turns out that to write this function an [Applicative](../src/main/scala/org/typeclassopedia/Applicative.scala) is required but its kind of an incidental detail, the important point is what `traverse` does.

For our user example:

``` scala
val result: IO[List[UserDetails]] = users.traverse(userDetails)
```

## Basic Theory

## Types, Kinds and Type Constructors

A *variable* has a *type*. For example, `x: Int` means the variable `x` has the type `Int`.

A *proper type*, also known as an *inhabited* type, is a type that can have values. The type Int is a proper type because it has values like 0 and 1.
`List[Int]` is also an inhabited, or proper type, that is inhabited by values like `1 :: 2 :: Nil`, instances of a list.

A *type constructor* is something that takes a type and produces a type. Examples are anything with type parameters like `List` or Option.
Type constructors are not inhabited, it is not possible to have values of `List` or Option, only List\[X\] or Option\[Y\].

In type theory it is useful to denote different *kinds* of types so that we can talk more generally about types, type constructors, etc.

### Extra Theory

Types are denoted with an asterisk: *Int* is of type \*

A type constructor taking a single type is denoted like this: `* -> *`, meaning that given a type, denoted by the first \*, it will produce a new proper type, \*. For example, A `List` given an Int will produce List\[Int\].

Something like Either\[A, B\], which takes two type parameters, is denoted like this: `*-> * -> *` because it takes two proper types and produces a type.
`Either[Int, String]` is a proper type constructed with Either and two proper types, Int and String.

*Kinds* are a way of describing similar types at an abstract level. `*`, `* -> *`, `* -> * -> *`, are *kinds*. Kinds are useful when working more abstractly with types, both proper and improper (inhabited or uninhabited).

Further reading [Wikipedia](https://en.wikipedia.org/wiki/Kind_(type_theory)) [Types of a Higher Kind](http://blogs.atlassian.com/2013/09/scala-types-of-a-higher-kind/) [Stack Exchange](http://programmers.stackexchange.com/a/255928/18311)

## Typeclasses

Typeclasses are an extremely common pattern used extensively in libraries like the [Typelevel](http://typelevel.org/) libraries. Martin Odersky et al describe typeclasses as follows:

> Type classes are useful to solve several fundamental challenges in software engineering and programming languages. In particular type classes support retroactive extension: the ability to extend existing software modules with new functionality without needing to touch or re-compile the original source.

Read more here: [Type Classes as Objects and Implicits](http://ropas.snu.ac.kr/~bruno/papers/TypeClasses.pdf)

In Scala, the typeclass pattern consists of several parts:

-   a trait with one or more type parameters that defines some behaviour
-   an optional companion object of the trait with *implicit* instances of the trait for common types like Strings or Options, and other types present in the standard library.

For example, a trait with a show function to transform a T into a String

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

    // This show requires a Show[T] so its a function
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

## Common types and what they represent

It is not uncommon to read blogs or other articles in which the follow statements are made:

-   Lists represent indeterminism
-   Either is a disjoint union
-   etc.

What do they mean?

## Option (or Maybe)

Option represents an optional value, or sometimes, success and failure.

It has two constructors: None and Some\[T\], where Some\[T\] contains a value of type T.

Option is of kind `* -> *`

## List

Lists represent lists of things, but they are often referred to as representing *indeterminism*.
A function returning a list can return any number of values including nothing (the empty list), hence the list is being used to represent an indeterministic result.

Lists are *recursive* data structures because they are typically defined in terms of themselves: a list is either the empty list, Nil, or a single element followed by a list.

List is of kind `* -> *`

## Either

Either is of kind `* -> * -> *`

## Validation

## Identity

Intuitively it simply wraps a value, it does not embody anything meaningful but is useful in some contexts beyond the scope of this document.

Identity is of kind `* -> *`

## Further Reading

* [Typeclassopedia](http://www.cs.tufts.edu/comp/150FP/archive/brent-yorgey/tc.pdf)
* [Modeling Uncertain Data using Monads and an Application to the Sequence Alignment Problem](https://pp.ipd.kit.edu/uploads/publikationen/kuhnle13bachelorarbeit.pdf)
* [Functors, Applicatives, And Monads In Pictures](http://adit.io/posts/2013-04-17-functors,_applicatives,_and_monads_in_pictures.html)
