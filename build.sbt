name := "telegramBotApi"

version := "1.0"
val Http4sVersion = "0.21.16"
val Sttp = "3.2.3"
scalaVersion := "2.12.8"
useCoursier := false
resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies ++= Seq(
  "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s"      %% "http4s-blaze-client" % Http4sVersion,
  "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
  "com.bot4s"       %% "telegram-core"       % "4.4.0-RC2",
  "com.bot4s" %% "telegram-akka" % "4.4.0-RC2",
)
