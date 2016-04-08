name := "paintnguess"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.postgresql" % "postgresql" % "9.4.1208.jre7"
)

play.Project.playJavaSettings
