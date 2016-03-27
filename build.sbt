name := "paintnguess"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  // If you enable PlayEbean plugin you must remove these
  // JPA dependencies to avoid conflicts.
  javaJpa,
  javaJdbc,
  "org.postgresql" % "postgresql" % "9.4-1200-jdbc4",
  "org.hibernate.javax.persistence" % "hibernate-jpa-2.1-api" % "1.0.0.Final",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.7.Final"
)

PlayKeys.externalizeResources := false