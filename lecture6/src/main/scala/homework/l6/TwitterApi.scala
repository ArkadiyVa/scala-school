package homework.l6
/**
  * Вам необходимо реализовать api для создания твиттов, получения твитта и лайка твитта
  *
  * Создание твитта:
  * На вход к вам поступает CreateTweetRequest, из которого вы должны создать объект Tweet, обязательно проверив длину текста (а может быть потом появятся и другие проверки).
  * hashTags вычисляется из tweet.text, собирая все слова, начинающиеся с символа `#`.
  * tweet.id генерируется из `UUID.randomUUID.toString`.
  * При условии прохождения всех проверок твит сохраняется в базу данных (вы можете реализовать любой способ сохранения: в памяти, запись в файл, или то, что вам захочется).
  * После выполнения всех операций должен вернуться созданный объект.
  *
  * Получение твитта:
  * На вход к вам поступает GetTweetRequest, вернуть вы должны объект tweet, если он найдем по id.
  *
  * Лайк твитта:
  * Должен обновлять количество лайков у твитта и возвращать новое значение. Если твит не найдет, то должно кидаться исключение NoTweetFoundException
  */

import java.time.Instant

case class Tweet(id: String,
                 user: String,
                 text: String,
                 hashTags: Seq[String] = Seq.empty,
                 createdAt: Option[Instant] = None,
                 likes: Int)

class NoTweetFoundException(id: String)
  extends Exception(s"Cannot find tweet=$id")
class IncorrectLengthException extends Exception(s"Incorrect length tweet")

trait Request
case class CreateTweetRequest(text: String, user: String) extends Request
case class GetTweetRequest(id: String) extends Request
case class LikeRequest(id: String) extends Request

object TwitterApi extends App {
  val regExTweet = "(#[A-Za-z0-9_]+)".r
  val tweetsMap = scala.collection.mutable.Map.empty[String, Tweet]

  trait Creator {

    def getId: String = {
      java.util.UUID.randomUUID.toString

    }

    def getHashTag(text: String): Seq[String] = {
      (regExTweet findAllIn text).toList
    }
    def validate(crt: CreateTweetRequest): Boolean = {
      crt.text.length < 280
    }

    def createTweet(crt: CreateTweetRequest): Tweet = {
      val newTweetId = getId
      if (validate(crt)) {
        tweetsMap += (newTweetId -> Tweet(newTweetId,
          crt.user,
          crt.text,
          getHashTag(crt.text),
          None,
          0))
        tweetsMap(newTweetId)
      } else throw new IncorrectLengthException
    }
  }

  trait getTweetReq {

    def getTweet(gtr: GetTweetRequest): Tweet = {
      val tweet = tweetsMap.get(gtr.id)
      if (tweet.isDefined) tweetsMap(gtr.id)
      else throw new NoTweetFoundException(gtr.id)
    }

  }
  trait likeReq {

    def updateLikes(tweet: Tweet): Tweet = {
      tweet.copy(createdAt = Some(Instant.now()), likes = tweet.likes + 1)
    }

    def likeTweet(lr: LikeRequest): Tweet = {
      val tweet = tweetsMap.get(lr.id)

      tweet map { _ =>
        val updatedTweet = updateLikes(tweet.get)
        tweetsMap += (lr.id -> updatedTweet)
        updatedTweet
      } getOrElse (throw new NoTweetFoundException(lr.id))
    }

  }
}