val scala3Version = "3.0.0"

val scalaTestVersion = "3.2.9"

lazy val root = project
  .in(file("."))
  .settings(
    version := "1.0",
    name := "typeclassopedia",
    scalaVersion := scala3Version,
    
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
    
    libraryDependencies += "org.scalactic" %% "scalactic" % scalaTestVersion,
    libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
    
)
