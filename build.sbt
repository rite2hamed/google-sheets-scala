name := "google-sheets-scala"

version := "0.1"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  "com.google.gdata" % "core" % "1.47.1",
  "com.google.apis" % "google-api-services-oauth2" % "v2-rev83-1.19.1",
  "com.google.apis" % "google-api-services-drive" % "v2-rev160-1.19.1",
  "com.netaporter" %% "scala-uri" % "0.4.4",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.4.4",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.4.4",
  "org.scalaj" %% "scalaj-http" % "1.1.0"
).map(
    _.exclude("org.mortbay.jetty", "jetty")
      .exclude("org.mortbay.jetty", "servlet-api")
      .exclude("org.mortbay.jetty", "jetty-util")
  )

unmanagedResourceDirectories in Runtime := Seq(baseDirectory.value / "src")