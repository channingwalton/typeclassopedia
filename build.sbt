name := "typeclassopedia"

version := "1.0"

scalaVersion := "2.11.4"

scalaBinaryVersion := "2.11"

updateOptions := updateOptions.value.withCachedResolution(true)

resolvers ++= Seq(
    "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
    "releases"  at "http://oss.sonatype.org/content/repositories/releases")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.11.6" % "test"

wartremoverErrors ++= Warts.allBut(Wart.NoNeedForMonad) // NoNeedForMonad crashes the compiler https://github.com/puffnfresh/wartremover/issues/106

scalacOptions ++= Seq(
    "-language:_",
    "-Xfatal-warnings",
    "-deprecation",
    "-encoding", "UTF-8",       // yes, this is 2 args
    "-feature",
    "-unchecked",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",        // N.B. doesn't work well with the ??? hole
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture"
    //"-Xlog-implicits"
)
