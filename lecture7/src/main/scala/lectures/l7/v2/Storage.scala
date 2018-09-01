//package lectures.l7.v2
//
//import lectures.l7.{Cat, Dog, Husky, Item}
//
///*
// * Реализовать класс, который будет хранить в памяти элементы с типом V и ключом K
// * Элементы должны реализовывать интерфейс Item, с типом ключа K
// * Реализовать метод, который будет принимать только один элемент для сохранения, без явного указания ключа
// * Реализовать объект компаньен, который предоставит возможность создавать пустой MemoryStorage и MemoryStorage с одним элементом.
// *
// * В отличие от реализации в v1, данном случае MemoryStorage должен быть специализирован для конкретного типа элементов, например Dog, Cat
// * Способ использования смотреть в Main
// */
//object Storage2 extends App {
//
//  import lectures.l7.Item
//
//  trait Storage[K, V] {
//    def persist(id: K, item: V): Storage[K, V]
//  }
//
//  class MemoryStorage[T,K](item: Item[T]) extends Storage[T, Item[T]] {
//    def persist(id: T, item: Item[T]) = MemoryStorage.apply(item)
//
//    object MemoryStorage {
//      //  def apply:MemoryStorage[T]= new MemoryStorage[T]()
//      def apply(x1: Item[T])= new MemoryStorage[T,K](x1)//23423423423
//
//    }
//
//    def persist(item: Item[T]):Storage[T, Item[T]] = {
//      MemoryStorage.apply(item)
//    }
//
//  }
//
//
//
//  val storage1: MemoryStorage[String, Dog] = new MemoryStorage[String, Dog](Dog("a", "b")).persist(Dog("b", "c")).persist(Husky("x"))
//  println(storage1)
//}