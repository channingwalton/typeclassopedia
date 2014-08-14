name := "typeclassopedia"

version := "1.0"

scalaVersion := "2.11.2"

scalaBinaryVersion := "2.11"

scalacOptions := Seq("-language:_", "-deprecation")

resolvers ++= Seq(
    "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
    "releases"  at "http://oss.sonatype.org/content/repositories/releases")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.11.5" % "test"