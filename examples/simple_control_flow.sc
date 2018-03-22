import $file.`dep`
import $ivy.`danslapman::cats-conts:0.4`

import cats._
import cats.conts._
import cats.syntax.applicative._
import cats.syntax.cps._

/*
  `prog` is similiar to the following program (written with scala-continuations):

  var cont: (Unit => Unit) = null

  reset {
    println("Before shift")
    shift { k: (Unit => Unit) =>
      cont = k
      println("Inside shift")
    }
    println("After shift")
  }
  println("After reset")
 */

val prog = for {
  _ <- println("Before shift").pure[Id].cps_
  _ <- Cont[Unit, Unit](cont => {println("Inside shift"); cont()})
  _ <- println("After reset").pure[Id].cps_
} yield ()

prog.run(_ => println("After shift"))