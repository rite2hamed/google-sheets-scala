package models

case class GoogleCredential(
  id: Option[Long],
  userId: Long,
  accessToken: String,
  refreshToken: String
)