package lectures.l7.v1

import lectures.l7.{Cat, Dog, Husky}

/*
 * Реализовать класс, который будет хранить в памяти элементы с типом V и ключом K
 * Элементы должны реализовывать интерфейс Item, с типом ключа K
 * Реализовать метод, который будет принимать только один элемент для сохранения, без явного указания ключа
 * Реализовать объект компаньен, который предоставит возможность создавать пустой MemoryStorage и MemoryStorage с одним элементом.
 *
 * Способ использования смотреть в Main
 */
object Storage1 extends App {


  import lectures.l7.Item

  trait Storage[K, V] {
    def persist(id: K, item: V): Storage[K, V]

  }

  class MemoryStorage[T](item:Item[T]) extends Storage[T, Item[T]] {
     def persist(id: T, item: Item[T]): Storage[T, Item[T]] = MemoryStorage.apply(item)
    object MemoryStorage{
    //  def apply:MemoryStorage[T]= new MemoryStorage[T]()
      def apply(x1:Item[T]):MemoryStorage[T]= new MemoryStorage[T](x1)

    }

    def persist(item: Item[T])  = {
      MemoryStorage.apply(item)
    }

  }
    val storage1: MemoryStorage[String] = new MemoryStorage[String](Dog("a", "b")).persist(Dog("b", "c")).persist(Husky("x")).persist(Cat("b", "c"))
    println(storage1)


}