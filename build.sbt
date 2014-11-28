name := "typeclassopedia"

version := "1.0"

scalaVersion := "2.11.4"

scalaBinaryVersion := "2.11"

resolvers ++= Seq(
    "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
    "releases"  at "http://oss.sonatype.org/content/repositories/releases")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.11.6" % "test"

wartremoverErrors ++= Seq(
    Wart.Any,
    Wart.Any2StringAdd,
    Wart.EitherProjectionPartial,
    Wart.OptionPartial,
    Wart.Product,
    Wart.Serializable,
    Wart.ListOps,
    Wart.Nothing
)

scalacOptions ++= Seq(
    "-language:_",
    "-Xfatal-warnings",
    "-deprecation",
    "-encoding", "UTF-8",       // yes, this is 2 args
    "-feature",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",        // N.B. doesn't work well with the ??? hole
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture"
)
