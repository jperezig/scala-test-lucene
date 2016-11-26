package org.ninit.autosuggest

import java.io.{File, FileInputStream}

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.analysis.util.CharArraySet
import org.apache.lucene.search.suggest.FileDictionary
import org.apache.lucene.search.suggest.analyzing.FuzzySuggester
import resource._

import scala.collection.JavaConverters._

case class Suggestion(text: String, weight: Float)

class Autosuggest(file: File) {

  val QueryAnalyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET)
  val IndexAnalyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET)

  val suggester = new FuzzySuggester(IndexAnalyzer, QueryAnalyzer)
  for {
    inputStream <- managed(new FileInputStream(file))
  } {
    suggester.build(new FileDictionary(inputStream))
  }

  def suggest(input: String, numResults: Int): Vector[Suggestion] = {
    suggester.lookup(input, false, numResults).asScala.map { result =>
      Suggestion(result.key.toString, result.value)
    }.toVector
  }
}

object Autosuggest extends App {

  val suggester = new Autosuggest(new File(args.head))

  suggester.suggest(args.tail.mkString(" "), 10).foreach(r => println(s"'${r.text}' (${r.weight})"))

}
