package homework.l3

import scala.collection.mutable.ListBuffer

sealed trait Box
case class PlayStationBox() extends Box
case class GuitarBox() extends Box
case class EaselBox() extends Box
case class BasicBox() extends Box {
  val size: Int = 4
}
case class BigBox() extends Box {
  val size: Int = 10
}

trait Stuff
case class PlayStation() extends Stuff
case class Guitar() extends Stuff
case class TV(size: Int) extends Stuff
case class Easel() extends Stuff
case class Book() extends Stuff
case class Cat() extends Stuff
case class Uculele() extends Stuff
case class Dish() extends Stuff
case class Shoes() extends Stuff

object BoxPlan extends App {
  val boxes = scala.collection.mutable.ListBuffer.empty[Box]
  val emptyPlaceInBigBox = scala.collection.mutable.ListBuffer.empty[Int]
  val emptyPlaceInBasicBox = scala.collection.mutable.ListBuffer.empty[Int]

  def updateEmptyPlace(EmptyPlace: ListBuffer[Int], TvSize: Int): Unit = {
    EmptyPlace.update(EmptyPlace.indexOf((EmptyPlace find (_ >= TvSize)).get),
      (EmptyPlace find (_ >= TvSize)).get - TvSize)
  }
  def undefined() = throw new IllegalArgumentException("incorrect TV size ")
  def plan(stuff: Seq[Stuff]): Seq[Box] = {
    stuff.foreach { x =>
      x match {
        case _: PlayStation                           => boxes += PlayStationBox()
        case _: Guitar                                => boxes += GuitarBox()
        case _: Easel                                 => boxes += EaselBox()
        case tv: TV if (tv.size > 10 || tv.size == 0) => undefined()
        case tv: TV if (tv.size > 5 && tv.size <= 10) => {
          emptyPlaceInBigBox += 10 - tv.size
          boxes += BigBox()
        }
        case tv: TV if (tv.size > 0 && tv.size < 6) => {
          if ((emptyPlaceInBigBox find (_ >= tv.size)) isDefined)
            updateEmptyPlace(emptyPlaceInBigBox, tv.size)
          else {
            if (tv.size == 5) {
              emptyPlaceInBigBox += 10 - tv.size
              boxes += BigBox()
            } else {
              if (tv.size == 4) {
                boxes += BasicBox()
              } else {
                if ((emptyPlaceInBasicBox find (_ >= tv.size)) isDefined)
                  updateEmptyPlace(emptyPlaceInBasicBox, tv.size)
                else {
                  emptyPlaceInBasicBox += 4 - tv.size
                  boxes += BasicBox()
                }
              }
            }
          }
        }
        case _: Stuff => {
          if ((emptyPlaceInBigBox find (_ >= 1)) isDefined) {
            updateEmptyPlace(emptyPlaceInBigBox, 1)
          } else {
            if ((emptyPlaceInBasicBox find (_ >= 1)) isDefined) {
              updateEmptyPlace(emptyPlaceInBasicBox, 1)
            } else {
              emptyPlaceInBasicBox += 3
              boxes += BasicBox()
            }
          }
        }
        case unexpected => undefined()
      }
    }
    boxes
  }
}