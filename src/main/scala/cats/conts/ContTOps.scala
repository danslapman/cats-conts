package cats.conts

import cats.FlatMap

import scala.language.higherKinds
import scala.language.implicitConversions

final class ContTOps[M[_], A](private val self: M[A]) extends AnyVal {
  def cps[R](implicit M: FlatMap[M]): ContT[M, R, A] =
    ContT((f: A => M[R]) => M.flatMap(self)(f))

  def cps_(implicit M: FlatMap[M]): ContT[M, Unit, A] =
    cps[Unit]
}

trait ToContTOps {
  implicit def ToContTOps[M[_], A](ma: M[A]): ContTOps[M, A] = new ContTOps(ma)
}
