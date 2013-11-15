name := "typeclassopedia"

version := "1.0"

scalaVersion := "2.10.3"

scalaBinaryVersion := "2.10"

scalacOptions := Seq("-language:_", "-deprecation")

resolvers ++= Seq(
    "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
    "releases"  at "http://oss.sonatype.org/content/repositories/releases")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.0" % "test"

libraryDependencies += "org.pegdown" % "pegdown" % "1.2.1" % "test"

unmanagedSourceDirectories in Compile := Seq(file("src/main/scala") )

unmanagedSourceDirectories in Test := Seq(file("src/test/scala") )

testOptions in Test += Tests.Argument("-h","target/html-test-report")
