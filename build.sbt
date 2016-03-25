name := "typeclassopedia"

version := "1.0"

scalaVersion := "2.11.8"

scalaBinaryVersion := "2.11"

updateOptions := updateOptions.value.withCachedResolution(true)

resolvers ++= Seq(
    "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
    "releases"  at "http://oss.sonatype.org/content/repositories/releases")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"

scalacOptions ++= Seq(
    "-language:_",
    "-Xfatal-warnings",
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-unchecked",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture",
    "-Ywarn-unused-import",
    "-Yno-predef",
    "-Yno-imports"
    //"-Xlog-implicits"
)
