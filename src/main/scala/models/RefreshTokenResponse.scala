package models

case class RefreshTokenResponse(
  accessToken: String,
  expiresIn: Int,
  tokenType: String
)

