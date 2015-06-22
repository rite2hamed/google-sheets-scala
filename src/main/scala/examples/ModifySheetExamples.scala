package examples

import models.GoogleCredential
import services.GoogleSheetsService

import scala.io.StdIn

object ModifySheetExamples {

  def main(args: Array[String]) {

    println("************************************")
    println("Enter an access code")
    println("************************************")

    print("access code: ")

    val code = StdIn.readLine()

    //this will fail if the access token needs refreshed.
    val creds = new GoogleCredential(None, -1, code, "")

    println("************************************")
    println("Enter a sheet id")
    println("************************************")

    print("sheet id: ")

    val fileid = StdIn.readLine()

    println("************************************")
    println("Enter a worksheet name")
    println("************************************")

    print("worksheet name: ")

    val worksheetName = StdIn.readLine()

    val service = new GoogleSheetsService()

    val worksheet = service.getWorksheetByName(creds, fileid, worksheetName)

    println("************************************")
    println("Your worksheet: %s".format(worksheet))
    println("************************************")

    println("")

    println("************************************")
    println("What would you like to write?")
    println("************************************")

    print("what to write: ")

    val whatToWrite = StdIn.readLine()

    val result = service.testWrite(creds, worksheet, whatToWrite)

    println("************************************")
    println("Done!")
    println("************************************")

  }
}
