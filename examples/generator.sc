import $file.dep
import $ivy.`danslapman::cats-conts:0.5`

import cats._
import cats.conts._

/*
  Reimplementation of https://gist.github.com/arnolddevos/574873
 */

class Generator[A] extends Iterator[A] with (A => Cont[Unit, Unit]) {
  private var a: Option[A] = None
  private var k: Option[Unit => Unit] = None

  def next = {
    val a0 = a.get
    val k0 = k.get
    a = None
    k = None
    k0()
    a0
  }

  def hasNext = k.isDefined

  def apply(a0: A): Cont[Unit, Unit] = {
    a = Some(a0)
    Cont(k0 => k = Some(k0))
  }
}

object Generator {
  def generator[A](f: (A => Cont[Unit, Unit]) => Cont[Unit, Unit]): Iterator[A] = {
    val g = new Generator[A]()
    f(g).run_
    g
  }

  trait SuspendableForeach[A]{ def foreach( f: A => Cont[Unit, Unit]): Cont[Unit, Unit] }

  def suspendable[A]( ible: Iterable[A]) = new SuspendableForeach[A] {
    def foreach(f: A => Cont[Unit, Unit]): Cont[Unit, Unit] = {
      val i: Iterator[A] = ible.iterator
      def while1(): Cont[Unit, Unit] =
        if (i.hasNext)
          f(i.next()).flatMap(_ => while1())
        else
          Cont[Unit, Unit](_())
      while1()
    }
  }
}

def example = Generator.generator[String] { yld =>
  for {
    _ <- yld( "first" )
    _ <- for( i <- Generator.suspendable(List(1,2,3)); j <- Generator.suspendable(List(4,5,6))) {
      yld((i*j).toString)
    }
    _ <- yld("last")
  } yield ()
}

for(a <- example) println(a)