package lectures.l2

import scala.annotation.tailrec

/**
  * Цель упражнения: вычислить 9 - е число Фибоначчи
  * Для этого раскомментируйте строчку в методе fibs и исправьте ошибку компиляции.
  *
  * Данная реализация вычисления чисел фибоначчи крайне не оптимальна (имеет показатеьную сложность O(a.pow(n)) )
  * Для того, что бы в этом убедиться, Вы можете раскомментировать
  * строчку с вычислением 1000-ого числа фибоначчи
  *
  */
object Fibonacci extends App {

  // Task 2
  def fibs(n: Int): Int = {

    @tailrec
    def fibsImpl(cur: Int, next: Int, n: Int): Int =
      n match {
        case 0 => cur
        case _ => fibsImpl(next, cur + next, n - 1)
      }

    fibsImpl(0, 1, n)
  }

  println(fibs(10))
  println(fibs(1000))
}

/**
  * Цель упражнения: используя приемы динамического программирования,
  * реализовать более оптимальный алгоритм подсчета чисел фибоначчи
  * Для этого нужно реализовать функцию fibsImpl.
  * Сигнатуру функции Вы можете расширять по своему усмотрению,
  * но реализация должна удовлетворять следующим требованиям
  * * * * метод fibsImpl - должен быть tail recursive
  * * * * параметр acc - аккумулятор посчитанных значений
  *
  */
object Fibonacci2 extends App {

  def fibs2(num: Int) =
    if (num <= 3) Array(1, 1, 2)(num - 1)
    else fibsImpl(num, Array(1, 1, 2))(num - 1)

  private def fibsImpl(n: Int, acc: Array[Int]): Array[Int] =
    n match {
      case 0 => acc
      case _ => fibsImpl(n - 1, acc :+ acc.takeRight(2).sum)
    }

  //  println(fibs2(10))
  println(fibs2(1000))
}