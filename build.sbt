name := "paintnguess"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  // If you enable PlayEbean plugin you must remove these
  // JPA dependencies to avoid conflicts.
  javaJdbc,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.7.Final",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"
)
