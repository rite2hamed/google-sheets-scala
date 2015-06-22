package examples

import models.User
import services.AuthService

import scala.io.StdIn

object AuthExamples {

  def main(args: Array[String]) {

    val authService = new AuthService()

    println("************************************")
    println("input the email you'd like to use  ")
    println("************************************")

    println("")

    print("email: ")

    val email = StdIn.readLine()

    val fakeUser = User(1,email)

    println("************************************")
    println("open this in your favorite browser  ")
    println("************************************")

    val fakeUserUri = authService.userUri(fakeUser)

    println("")

    println(fakeUserUri)

    println("")

    println("************************************")
    println("then input the access code you received")
    println("************************************")

    println("")

    print("access code: ")

    val code = StdIn.readLine()

    val googleCredential = authService.buildCredential(
      code,
      fakeUser
    )

    println("This is your google credential: ")

    println("Access token: %s".format(googleCredential.accessToken))
    println("Refresh token: %s".format(googleCredential.refreshToken))
  }
}
