package cats

import scala.language.higherKinds

package object conts {
  type IndexedConts[W[_], R, O, A] = IndexedContsT[W, Id, R, O, A]
  object IndexedConts extends IndexedContsTInstances with IndexedContsTFunctions {
    def apply[W[_], R, O, A](f: W[A => O] => R): IndexedConts[W, R, O, A] = IndexedContsT[W, Id, R, O, A](f)
  }

  type IndexedContT[M[_], R, O, A] = IndexedContsT[Id, M, R, O, A]
  object IndexedContT extends IndexedContsTInstances with IndexedContsTFunctions {
    def apply[M[_], R, O, A](f: (A => M[O]) => M[R]): IndexedContT[M, R, O, A] = IndexedContsT[Id, M, R, O, A](f)
  }

  type IndexedCont[R, O, A] = IndexedContT[Id, R, O, A]
  object IndexedCont extends IndexedContsTInstances with IndexedContsTFunctions {
    def apply[R, O, A](f: (A => O) => R): IndexedCont[R, O, A] = IndexedContsT[Id, Id, R, O, A](f)
  }

  type ContsT[W[_], M[_], R, A] = IndexedContsT[W, M, R, R, A]
  object ContsT extends IndexedContsTInstances with IndexedContsTFunctions {
    def apply[W[_], M[_], R, A](f: W[A => M[R]] => M[R]): ContsT[W, M, R, A] = IndexedContsT[W, M, R, R, A](f)
  }

  type Conts[W[_], R, A] = ContsT[W, Id, R, A]
  object Conts extends IndexedContsTInstances with IndexedContsTFunctions {
    def apply[W[_], R, A](f: W[A => R] => R): Conts[W, R, A] = IndexedContsT[W, Id, R, R, A](f)
  }

  type ContT[M[_], R, A] = ContsT[Id, M, R, A]
  object ContT extends IndexedContsTInstances with IndexedContsTFunctions {
    def apply[M[_], R, A](f: (A => M[R]) => M[R]): ContT[M, R, A] = IndexedContsT[Id, M, R, R, A](f)
  }

  type Cont[R, A] = ContT[Id, R, A]
  object Cont extends IndexedContsTInstances with IndexedContsTFunctions {
    def apply[R, A](f: (A => R) => R): Cont[R, A] = IndexedContsT[Id, Id, R, R, A](f)
  }
}
