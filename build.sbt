ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "KubeCtl",
    libraryDependencies ++= Seq(
      "io.kubernetes" % "client-java" % "15.0.1",
      "io.kubernetes" % "client-java-extended" % "15.0.1",
    )
  )
