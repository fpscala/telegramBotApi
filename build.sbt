name := "telegramBotApi"

version := "1.0"
val Http4sVersion = "0.21.16"
val Bot4sVersion = "5.0.0"
scalaVersion := "2.13.6"
useCoursier := false
resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
  "org.http4s" %% "http4s-dsl"          % Http4sVersion,
  "com.bot4s"  %% "telegram-core"       % Bot4sVersion
)
