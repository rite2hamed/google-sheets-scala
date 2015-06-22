package services

import java.net.URL

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.gdata.client.spreadsheet.SpreadsheetService
import com.google.gdata.data.spreadsheet.{CellEntry, CellFeed, SpreadsheetEntry, WorksheetEntry}
import models.GoogleApp

import scala.collection.JavaConversions._

// For altering sheets we need to use the gData services

class GoogleSheetsService {

  lazy val app = Json.fromJson[GoogleApp](this.getClass.getClassLoader.getResource("google_secrets.json"))

  private val sheetsFeedBase = "https://spreadsheets.google.com/feeds/spreadsheets/"

  def gDataApiForToken(accessToken: String) = {
    val service = new SpreadsheetService("beekeeper")

    val clientId = app.clientId
    val clientSecret = app.clientSecret
    val httpTransport = new NetHttpTransport
    val jsonFactory = new JacksonFactory

    val credential = new GoogleCredential.Builder()
      .setJsonFactory(jsonFactory)
      .setTransport(httpTransport)
      .setClientSecrets(clientId, clientSecret)
      .build()
    credential.setAccessToken(accessToken)

    service.setHeader("Authorization", "Bearer " + accessToken)

    service.setOAuth2Credentials(credential)

    service
  }

  def getWorksheetByName(creds: models.GoogleCredential, fileId: String, worksheetName: String) = {

    val service = gDataApiForToken(creds.accessToken) //see above

    val metafeedUrl = new URL(sheetsFeedBase+fileId)

    val spreadsheet = service.getEntry(metafeedUrl, classOf[SpreadsheetEntry])

    spreadsheet.getWorksheets.find(_.getTitle.getPlainText.equals(worksheetName)).head
  }

  def testWrite(creds: models.GoogleCredential, worksheetEntry: WorksheetEntry, whatToWrite: String) = {

    val service = gDataApiForToken(creds.accessToken) //see above

    val cellFeedUrl = worksheetEntry.getCellFeedUrl()
    val cellFeed = service.getFeed(cellFeedUrl, classOf[CellFeed])

    val cellEntry = new CellEntry(1, 1, whatToWrite)
    cellFeed.insert(cellEntry)
  }

}
