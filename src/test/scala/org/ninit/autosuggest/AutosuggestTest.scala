package org.ninit.autosuggest

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by jperezi on 25/11/16.
  */
class AutosuggestTest extends FlatSpec with Matchers {

  "Autosuggest" should "autosuggest given an input" in {
    Autosuggest.suggest("camarero", 10).head.key shouldBe "camarero"
  }

  it should "return no more than max number of results" in {
    Autosuggest.suggest("camarero", 1).length shouldBe 1
  }
}
