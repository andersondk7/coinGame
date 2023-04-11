import sbt.Keys.libraryDependencies

val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "coingame",
    version := "0.1.0",

    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.8.0",
      "org.scalactic" %% "scalactic" % "3.2.13",
      "org.scalatest" %% "scalatest" % "3.2.13" % "test"
    )
  )

