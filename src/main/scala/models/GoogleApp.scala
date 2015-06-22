package models

case class GoogleApp(
  clientId: String,
  clientSecret: String,
  redirectUrl: String,
  scopes: List[String]
)
