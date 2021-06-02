name := "TelegramBotApi"
 
version := "1.0"
lazy val `telegrambotapi` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.13.5"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3",
  "com.softwaremill.sttp.client3" %% "akka-http-backend" % "3.3.6",
  "com.bot4s" %% "telegram-core" % "5.0.0"
)
      