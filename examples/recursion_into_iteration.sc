import $file.`dep`
import $ivy.`danslapman::cats-conts:0.4`

import cats._
import cats.conts._

import java.io._

/*
  This example is a port of code from Cay Horstmann's
  `Scala for impatient`
 */

/*
  Control context from `scala-continuations` in terms of IndexedContsT
 */
type ControlCtx[A, B, C] = IndexedContsT[Id, Id, C, B, A]
object ControlCtx extends IndexedContsTInstances with IndexedContsTFunctions {
  def apply[C, B, A](f: (A => B) => C): ControlCtx[A, B, C] = IndexedContsT[Id, Id, C, B, A](f)
}

var cont: Unit => Unit = null

def processDirectory(dir: File): ControlCtx[Unit, Unit, Unit] = {
  val files = dir.listFiles()
  var i = 0
  def while1(): ControlCtx[Unit, Unit, Unit] =
    if (i < files.length) {
      val cont1 = {
        val f = files(i)
        i += 1
        if (f.isDirectory)
          processDirectory(f)
        else
          ControlCtx[Unit, Unit, Unit] { k =>
            cont = k
          }.map(_ => println(f))
      }
      cont1.flatMap(_ => while1())
    } else {
      ControlCtx(_ => ())
    }
  while1()
}

processDirectory(new File("{some path}")).run_
for (i <- 1 to 100) cont()