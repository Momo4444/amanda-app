lazy val buildSettings = Seq(
  name := "amanda-app",
  version := "0.1",
  scalaVersion := "2.12.8",
)

lazy val commonScalacOptions = Seq(
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:existentials",
  "-language:postfixOps",
  "-Ypartial-unification",
)

lazy val commonSettings = Seq(
  scalacOptions ++= commonScalacOptions,
  libraryDependencies ++= List(
    "com.typesafe" % "config" % "1.3.1",
    "com.github.pureconfig" %% "pureconfig" % "0.7.0",
    "org.specs2" %% "specs2-core" % "4.3.2" % "test",
  )
)

lazy val root = (project in file("."))
  .settings(buildSettings)
  .settings(commonSettings)
