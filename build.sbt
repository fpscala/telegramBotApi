name := "telegramBotApi"

version := "1.0"

scalaVersion := "2.12.8"

resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies ++= Seq("com.bot4s" %% "telegram-core" % "4.4.0-RC2",
  "com.bot4s" %% "telegram-akka" % "4.4.0-RC2",
  "org.scalaj" %% "scalaj-http" % "2.4.2"
)
