lazy val root = project
  .in(file("."))
  .settings(
    version := "1.0",
    name := "typeclassopedia",
    scalaVersion := "3.6.4",
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test",
    libraryDependencies += "org.scalatestplus" %% "scalacheck-1-18" % "3.2.19.0" % "test"
)
