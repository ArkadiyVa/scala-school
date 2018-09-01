package homework.l4

/**
  * Не используя мутабельные коллекции и var, реализовать сортировку слиянием.
  * Подробнее о сортировке можно подсмотреть здесь - https://en.wikipedia.org/wiki/Merge_sort
  *
  *
  */
object MergeSort extends App {

  def mergeSort(data: Seq[Int]): Seq[Int] = {
    val middle = data.length / 2
    if (middle == 0) data
    else {
      val (left, right) = data.splitAt(middle)
      merge(mergeSort(left), mergeSort(right))
    }
  }
  def merge(left: Seq[Int], right: Seq[Int]): Seq[Int] = {
    if (left.isEmpty) right
    else if (right.isEmpty) left
    else if (left.head <= right.head) left.head +: merge(left.tail, right)
    else right.head +: merge(left, right.tail)
  }

}