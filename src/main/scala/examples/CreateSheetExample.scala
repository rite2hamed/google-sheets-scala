package examples

import services.GoogleDriveService

import scala.io.StdIn

object CreateSheetExample {

  def main(args: Array[String]) {

    println("************************************")
    println("Enter an access code")
    println("************************************")

    print("access code: ")

    val code = StdIn.readLine()

    println("************************************")
    println("Enter a file name")
    println("************************************")

    print("file name: ")

    val filename = StdIn.readLine()

    val service = new GoogleDriveService()

    val rslt = service.createSpreadsheetOnDrive(code, filename)

    println("************************************")
    println("Sheet created!")
    println("************************************")

    println("")

    println("************************************")
    println("Sheet details: %s".format(rslt))
    println("************************************")


  }

}
