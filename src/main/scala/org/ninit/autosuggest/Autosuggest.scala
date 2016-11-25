package org.ninit.autosuggest

import java.io.FileInputStream

import org.apache.lucene.analysis.es.SpanishAnalyzer
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.analysis.util.CharArraySet
import org.apache.lucene.search.suggest.FileDictionary
import org.apache.lucene.search.suggest.analyzing.{AnalyzingSuggester, FuzzySuggester}
import resource._

import scala.collection.JavaConverters._

object Autosuggest extends App {

  def suggest(input: String, numResults: Int) = {

    val QueryAnalyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET)
    val IndexAnalyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET)

    val suggester = new FuzzySuggester(IndexAnalyzer, QueryAnalyzer)

    val InputFile = "/home/jperezi/Ep-20161020-employments-position-1M-jt_min10_score.csv"

    for {
      inputStream <- managed(new FileInputStream(InputFile))
    } {
      suggester.build(new FileDictionary(inputStream))
    }

    suggester.lookup(input, false, numResults).asScala
  }

  suggest(args.mkString(" "), 10).foreach(r => println(s"'${r.key}'"))

}
