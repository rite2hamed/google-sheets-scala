package services

import com.netaporter.uri.dsl._
import models.{GoogleCredential, RefreshTokenResponse, TokenResponse, User}

import scalaj.http.{HttpResponse, _}

class AuthService {

  def userUri(user: User): String = {
    val creds = loadApp //see above
    val url = authUrlBase ?
        ("response_type" -> "code") &
        ("client_id"-> creds.clientId) &
        ("redirect_uri" -> creds.redirectUrl) &
        ("scope" -> creds.scopes.mkString(" ")) &
        ("state" -> "beekeeper!") &
        ("login_hint" -> user.email) &
        ("include_granted_scopes" -> "true")
    url.toString
  }

  def buildCredential(code: String, owner: User): GoogleCredential = {
    val creds = loadApp //see above
    val response: HttpResponse[String] = Http(tokenUrlBase).postForm(Seq(
        "code" -> code,
        "client_id" -> creds.clientId,
        "client_secret" -> creds.clientSecret,
        "redirect_uri" -> creds.redirectUrl,
        "grant_type" -> "authorization_code",
        "access_type" -> "offline"
      )).asString

    val tokenData = response.code match {
      case 200 => Json.fromJson[TokenResponse](response.body, true)
      case _ => throw new Exception("OAuth Failed with code %d: %s".format(response.code, response.body))
    }

    GoogleCredential(
      None,
      owner.id,
      tokenData.accessToken,
      tokenData.refreshToken
    )
  }

  def refreshCredential(googleCredential: GoogleCredential): GoogleCredential = {
    val creds = loadApp //see above
    val response: HttpResponse[String] = Http(tokenUrlBase).postForm(Seq(
        "refresh_token" -> googleCredential.refreshToken,
        "client_id" -> creds.clientId,
        "client_secret" -> creds.clientSecret,
        "grant_type" -> "refresh_token"
      )).asString

    val tokenData = response.code match {
      case 200 => Json.fromJson[RefreshTokenResponse](response.body, true)
      case _ => throw new Exception("OAuth Failed with code %d: %s".format(response.code, response.body))
    }

    googleCredential.copy(accessToken = tokenData.accessToken)
  }

}
