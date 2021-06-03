import sbt.Keys.libraryDependencies

val scala2_13 = "2.13.5"
val scala2_12 = "2.12.12"

lazy val projectSettings = Seq(
  version := "1.0",
  scalaVersion := scala2_12,
  crossScalaVersions := Seq(scala2_13, scalaVersion.value)
)

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

lazy val telegrambotapi = (project in file(".")).aggregate(core, server)
lazy val core = project
  .in(file("core"))
  .settings(
    name := "bot4scala",
    projectSettings
  )
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3",
      "org.scalaj" %% "scalaj-http" % "2.4.2",
      "org.json4s" %% "json4s-native" % "3.2.11"
    )
  )

lazy val server = project
  .in(file("server"))
  .settings(
    name := "server",
    projectSettings,
    crossScalaVersions := Seq(scalaVersion.value),
    libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice,
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3"
    )
  ).enablePlugins(PlayScala).dependsOn(core)
Global / onLoad := (Global / onLoad).value.andThen(state => "project server" :: state)