package scala.lectures.l8


sealed trait Equals[A] {
  def eq(left: A, right: A): Boolean
}
trait LowPriorityEq {
  implicit def simpleEq[A]: Equals[A] = new Equals[A] {
    def eq(left: A, right: A): Boolean = left == right
  }
}

object Eq extends LowPriorityEq {


  implicit def eqOption[T]: Equals[Option[T]] = new Equals[Option[T]] {
    def eq(left: Option[T], right: Option[T]): Boolean = {
      (left, right) match {
        case (None, None)                               => true
        case (Some(left), Some(right)) if left == right => true
        case _                                          => false
      }
    }
  }

  implicit def MapEq[T](implicit asymEq: Equals[T]): Equals[Map[String, T]] =
    new Equals[Map[String, T]] {
      def eq(left: Map[String, T], right: Map[String, T]): Boolean = {
        if (left.keys == right.keys) {
          left.forall(elem => asymEq.eq(elem._2, right(elem._1)))
        } else false
      }
    }

  implicit def seqEquals[A](implicit s: Equals[A]): Equals[Seq[A]] =
    new Equals[Seq[A]] {
      def eq(left: Seq[A], right: Seq[A]): Boolean =
        left.size == right.size && (left zip right).forall {
          case (l, r) => l === r
        }
    }

  implicit class Syntax[A](left: A) {
    def ===(right: A)(implicit asymEq: Equals[A]) = asymEq.eq(left, right)

    def !==(right: A)(implicit asymEq: Equals[A]) = !asymEq.eq(left, right)
  }

}
object NewEquals extends App {
  import Eq._

  assert(1 === 1)
  assert(1 !== 2)
  assert("1" === "1")
  assert("1" !== "2")
  assert(None === None)
  assert(Option(1) === Option(1))
  assert(Option(1) !== Option(2))
  assert(
    Map("1" -> 2, "3" -> 4, "5" -> 8) === Map("3" -> 4, "5" -> 8, "1" -> 2))
  assert(Map("1" -> 2) !== Map("1" -> 3))
  assert(Seq(1, 2) === Seq(1, 2))
  assert(Seq(1, 2) !== Seq(1, 2, 3))
  assert(Set(2, 1) === Set(1, 2))
}