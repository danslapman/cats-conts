import $file.dep
import $ivy.`danslapman::cats-conts:0.5`

import cats._
import cats.conts._

import java.io._

/*
  This example is a port of code from Cay Horstmann's
  `Scala for impatient`
 */

var cont: Unit => Unit = null

def processDirectory(dir: File): Cont[Unit, Unit] = {
  val files = dir.listFiles()
  var i = 0
  def while1(): Cont[Unit, Unit] =
    if (i < files.length) {
      val cont1 = {
        val f = files(i)
        i += 1
        if (f.isDirectory)
          processDirectory(f)
        else
          Cont[Unit, Unit] { k =>
            cont = k
          }.map(_ => println(f))
      }
      cont1.flatMap(_ => while1())
    } else {
      Cont(_ => ())
    }
  while1()
}

processDirectory(new File("/Users")).run_
for (i <- 1 to 100) cont()