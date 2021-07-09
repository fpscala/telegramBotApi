import sbt.Keys.libraryDependencies

val scala2_13 = "2.13.5"
val scala2_12 = "2.12.12"

lazy val projectSettings = Seq(
  version := "1.1",
  scalaVersion := scala2_12,
  crossScalaVersions := Seq(scala2_13, scalaVersion.value)
)

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

lazy val telegrambotapi = (project in file("."))
  .settings(
    name := "telegrambotapi",
    projectSettings,
    crossScalaVersions := Seq(scalaVersion.value),
    libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice,
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3",
      "uz.scala" %% "bot4scala" % "1.0.5"
    )
  ).enablePlugins(PlayScala)