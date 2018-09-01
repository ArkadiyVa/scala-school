package homework.l2


/**
  * Вам нужно реализовать функции sumOfSquares и multiplicationOfCubes
  * при помощи ListFunctions.fold, композиции и частичного применения функций, sum, multiply и pow
  * Можно добовлять промежуточные функции.
  * Также вам может понадобится функция Function.uncurry,
  * которая из карированной функции делает функцию с несколькими аргументами
  */
object ListHomework extends App {

  val sum = (a: Int, b: Int) => a + b

  val multiply = (a: Int, b: Int) => a * b

  def pow(a: Int, p: Int): Int = if(p <= 0) 1 else a * pow(a, p - 1)

  def pow2(s1: Int) = pow(s1, 2)
  def pow3(s1: Int) = pow(s1, 3)

  val resComposeSum = sum.curried(_:Int) compose pow2
  val resComposeMultiply = multiply.curried(_:Int) compose pow3

  /**
    * Сумма квадратов чисел в списке
    */
  lazy val sumOfSquares: List[Int] => Int = {
    List => ListFunctions.fold(0,List)(Function.uncurried(resComposeSum))
  }
  /**
    * Сумма кубов чисел в списке
    */
  lazy val multiplicationOfCubes: List[Int] => Int = {
    List => ListFunctions.fold(1, List)(Function.uncurried(resComposeMultiply))
  }
}