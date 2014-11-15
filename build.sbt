name := "typeclassopedia"

version := "1.0"

scalaVersion := "2.11.4"

scalaBinaryVersion := "2.11"

scalacOptions := Seq("-language:_", "-deprecation", "-Xlint", "-Xfatal-warnings")

resolvers ++= Seq(
    "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
    "releases"  at "http://oss.sonatype.org/content/repositories/releases")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.11.6" % "test"
