package lectures.l7.v3


/*
 * Реализация должна поддерживать все что в v2, но добавляется:
 * должен позволять помещать в хранилище объекты родительского типа С по-отношению к V,
 * результатом будет хранилище с ключом K и типом C
 *
 * Способ использования смотреть в Main
 *
 * Допустимо менять ограничения Type Parameter в исходном trait
 */
object Storage3 extends App {

  import lectures.l7.Item

  trait Storage[K, V] {
    def persist[C](id: K, item: C): Storage[K, C]
  }

  class MemoryStorage[T, K](item: Item[T]) extends Storage[T, Item[T]] {
    override def persist[C](id: T, item: C): Storage[T, C] = ???

    object MemoryStorage {
      //  def apply:MemoryStorage[T]= new MemoryStorage[T]()
      def apply(x1: Item[T]): MemoryStorage[T, K] = new MemoryStorage(x1)

    }

    def persist[C](item: Item[T]) = {
      MemoryStorage.apply(item)
    }

  }

}