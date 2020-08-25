val dottyVersion = "0.26.0-RC1"

lazy val root = project
  .in(file("."))
  .settings(
    version := "1.0",
    name := "typeclassopedia",
    scalaVersion := dottyVersion,
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
    libraryDependencies += "org.scalatest" % "scalatest_0.26" % "3.2.2" % "test",
    libraryDependencies += "org.scalatestplus" % "scalacheck-1-14_0.26" % "3.2.2.0" % "test"
)
