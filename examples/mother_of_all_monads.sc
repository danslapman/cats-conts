import $file.dep
import $ivy.`danslapman::cats-conts:0.5`

import cats._
import cats.conts._
import cats.instances.int._
import cats.instances.list._
import cats.syntax.applicative._
import cats.syntax.cps._
import cats.syntax.flatMap._
import cats.syntax.semigroup._

/*
  Port of examples from
  http://blog.sigfpe.com/2008/12/mother-of-all-monads.html
 */

val ex2 = for {
  a <- 1.pure[Id]
  b <- 10.pure[Id].cps[Int]
} yield a + b

println(ex2.run_)

val ex3 = for {
  a <- 1.pure[Id]
  b <- Cont[String, String](_ => "escape")
} yield a + b

println(ex3.run_)

val ex4 = for {
  a <- 1.pure[Id]
  b <- Cont[Int, Int](f => f(10) |+| f(20))
} yield a + b

println(ex4.run_)

val ex6 = for {
  a <- 1.pure[Id]
  b <- ContT[List, Int, Int](f => f(10) |+| f(20))
} yield a + b

println(ex6.run(_.pure[List]))

val ex8 = for {
  a <- 1.pure[Id]
  b <- ContT[List, Int, Int](f => List(10, 20) >>= f)
} yield a + b

println(ex8.run_)

val ex9 = for {
  a <- List(1, 2).cps[Int]
  b <- List(10, 20).cps[Int]
} yield a + b

println(ex9.run_)

val ex10 = for {
  _ <- println("What is your name?").pure[Id].cps_
  name <- scala.io.StdIn.readLine().pure[Id].cps_
  _ <- println(s"Merry Xmas $name").pure[Id].cps_
} yield ()

ex10.run_