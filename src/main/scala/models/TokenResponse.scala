package models

case class TokenResponse(
  accessToken: String,
  expiresIn: Int,
  tokenType: String,
  refreshToken: String
)