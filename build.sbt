lazy val buildSettings = Seq(
  name := "amanda-app",
  version := "0.1",
  scalaVersion := "2.12.8",
)

lazy val commonSettings = Seq(
  libraryDependencies ++= List(
    "com.typesafe" % "config" % "1.3.1",
    "com.github.pureconfig" %% "pureconfig" % "0.7.0",
  )
)

lazy val root = (project in file("."))
  .settings(buildSettings)
  .settings(commonSettings)
