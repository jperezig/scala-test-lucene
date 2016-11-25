name := "TestLucene"

version := "1.0"

scalaVersion := "2.12.0"

libraryDependencies ++=
  Dependencies.Lucene ++
    Dependencies.Test :+
    "com.jsuereth" %% "scala-arm" % "2.0"
