import sbt._

object Dependencies {

  val Test = Seq("org.scalatest" %% "scalatest" % "3.0.1" % "test")


  val LuceneVersion = "5.5.2"

  val Lucene = Seq(
    "org.apache.lucene" % "lucene-core" % LuceneVersion,
    "org.apache.lucene" % "lucene-suggest" % LuceneVersion,
    "org.apache.lucene" % "lucene-analyzers-common" % LuceneVersion
  )
}