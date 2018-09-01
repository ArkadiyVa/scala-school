
package lectures.l5
import org.scalatest.{FlatSpec, Matchers}

package object authentication {

  def packageFunc[T](user: User, requiredRole: String)(f: AuthUser => T): T =
    user match {
      case user @ AuthUser(_, _, `requiredRole`) => f(user)
      case _                                     => throw new IllegalArgumentException
    }

  trait User
  case class AuthUser(login: String, password: String, role: String) extends User
  case class UnAuthUser(login: String, password: String) extends User

}
object TestService {
  import authentication._

  class UserAlreadyAuthorizedException extends Exception("User alreay Auth")
  val logPas = Map(("Vasya", "123") -> "admin",
    ("Petya", "1234") -> "student",
    ("Kolya", "12345") -> "moderator")

  def checkLogPas(unAuth: UnAuthUser): Boolean = {
    logPas.get(unAuth.login, unAuth.password).isDefined
  }

  def authorize(user: User): AuthUser = {
    user match {
      case _: AuthUser => throw new UserAlreadyAuthorizedException()
      case unAuth @ UnAuthUser(login, password) if checkLogPas(unAuth) =>
        AuthUser(login, password, logPas(login, password))
      case _ => throw new IllegalArgumentException
    }
  }
}
class ServiceTest extends FlatSpec with Matchers {

  import TestService._
  import authentication._

  it should "IllegalArgumentException for authorization " in {
    assertThrows[IllegalArgumentException](
      authorize(UnAuthUser("Ivan", "1234"))
    )
  }
  it should "UserAlreadyAuthorizedException for authorization" in {
    assertThrows[UserAlreadyAuthorizedException](
      authorize(AuthUser("vasya1", "123", "123"))
    )
  }
  it should "Correct authorization for student" in {
    authorize(UnAuthUser("Petya", "1234")) shouldBe AuthUser("Petya",
      "1234",
      "student")
  }
  it should "Correct authorization for moderator" in {
    authorize(UnAuthUser("Kolya", "12345")) shouldBe AuthUser("Kolya",
      "12345",
      "moderator")
  }
  it should "IllegalArgumentException for packageFunc" in {
    assertThrows[IllegalArgumentException](
      packageFunc(UnAuthUser("Vasya", "123"), "admin")(_ => "It's a admin")
    )
  }
  it should "packageFunc incorrect authUser " in {
    assertThrows[IllegalArgumentException](
      packageFunc(AuthUser("Vasya", "123", "student"), "admin")(_ =>
        "It's a admin")
    )
  }
  it should "packageFunc correct authUser" in {
    packageFunc(AuthUser("Vasya", "123", "admin"), "admin")(_ => "It's a admin") shouldBe ("It's a admin")
  }

}