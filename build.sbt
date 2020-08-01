name := "typeclassopedia"

version := "1.0"

scalaVersion := "2.13.3"

scalaBinaryVersion := "2.13"

resolvers ++= Seq(
    "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
    "releases"  at "http://oss.sonatype.org/content/repositories/releases")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"
libraryDependencies += "org.scalatestplus" %% "scalatestplus-scalacheck" % "3.1.0.0-RC2" % "test"

scalafmtConfig in Compile := file(".scalafmt.conf")
scalafmtOnCompile in Compile := true
