package services

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import models.{DriveResource, GoogleApp}

// For creating & deleting Drive resources we need to use the drive service instead of the gData service

class GoogleDriveService {

  lazy val app = Json.fromJson[GoogleApp](this.getClass.getClassLoader.getResource("google_secrets.json"))

  def googleDriveApiForToken(accessToken: String): Drive = {

    val clientId = app.clientId
    val clientSecret = app.clientSecret
    val httpTransport = new NetHttpTransport
    val jsonFactory = new JacksonFactory

    //Build the Google credentials and make the Drive ready to interact
    val credential = new GoogleCredential.Builder()
      .setJsonFactory(jsonFactory)
      .setTransport(httpTransport)
      .setClientSecrets(clientId, clientSecret)
      .build()
    credential.setAccessToken(accessToken)
    //Create a new authorized API client
    new Drive.Builder(httpTransport, jsonFactory, credential).build()
  }

  def createSpreadsheetOnDrive(accessToken: String, nameOfFile: String) = {

    val service = googleDriveApiForToken(accessToken)
    val body = new File
    body.setMimeType("application/vnd.google-apps.spreadsheet")
    val docType = "spreadsheet"
    body.setTitle(nameOfFile)
    val file = service.files.insert(body).execute
    DriveResource(file.getAlternateLink, file.getTitle(), file.getThumbnailLink())
  }

}
