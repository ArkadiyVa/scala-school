package homework.l4

/**
  * В этом домашнем задании вам нужно реализовать калькулятор.
  * Что он должен делать:
  * функция computeValues принимает Map, ключами являются имена переменных, а значением некоторое выражение.
  * На выходе функция возвращает Map, в которой все выражения вычислены.
  * Функция eval вычисляет одно заданное выражение, а getReferenceExpr возвращает значение переменной по имени.
  * Функция eval возвращает Double.NaN в случаях:
  * - переменная не была задана
  * - циклическая ссылка, например: a = c - b; b = a - 2
  */
sealed abstract class Expr
final case class Literal(v: Double) extends Expr
final case class Ref(name: String) extends Expr
final case class Plus(a: Expr, b: Expr) extends Expr
final case class Minus(a: Expr, b: Expr) extends Expr
final case class Times(a: Expr, b: Expr) extends Expr
final case class Divide(a: Expr, b: Expr) extends Expr

object Calculator extends App {

  def computeValues(
                     namedExpressions: Map[String, Expr]): Map[String, Double] = {
    namedExpressions.map {
      case (label, expression) => label -> eval(expression, namedExpressions)
    }
  }

  def eval(expr: Expr, references: Map[String, Expr]): Double = {
    expr match {
      case Literal(v)   => v
      case Plus(a, b)   => eval(a, references) + eval(b, references)
      case Minus(a, b)  => eval(a, references) - eval(b, references)
      case Times(a, b)  => eval(a, references) * eval(b, references)
      case Divide(a, b) => eval(a, references) / eval(b, references)
      case Ref(name) =>
        eval(getReferenceExpr(name, references), references - name)
    }
  }

  // Данная функция должна вернуть значение переменной по имени
  def getReferenceExpr(name: String, references: Map[String, Expr]): Expr =
    references.get(name).fold[Expr](Literal(Double.NaN))(x => x)
}