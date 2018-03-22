import $file.`dep`
import $ivy.`danslapman::cats-conts:0.4`

import cats.conts._

import scala.annotation.tailrec

@tailrec
def rv[T](l: List[T])(f: List[T] => List[T]): List[T] =
  l match  {
    case Nil => f(Nil)
    case x :: xs => rv(xs)(f.andThen((h) => x :: h))
  }

println(rv(1 :: 2 :: 3 :: Nil)(identity))

def rvc[T](l: List[T]): Cont[List[T], List[T]] = Cont(f =>
  l match {
    case Nil => f(Nil)
    case x :: xs => rv(xs)(f.andThen((h) => x :: h))
  }
)
println(rvc(4 :: 5 :: 6 :: Nil).run(identity))