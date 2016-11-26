package org.ninit.autosuggest

import java.nio.charset.StandardCharsets
import java.nio.file.Files

import org.scalatest.{Matchers, Outcome, fixture}

class AutosuggestTest extends fixture.FlatSpec with Matchers {

  type FixtureParam = Autosuggest

  def withFixture(test: OneArgTest): Outcome = {
    val stringContent = "camarero\t10\ncamarera\t100\n"

    val path = Files.createTempFile("titles", "tmp")
    try {
      Files.write(path, stringContent.getBytes(StandardCharsets.UTF_8))
      val suggester = new Autosuggest(path.toFile)
      test(suggester)
    }
    finally {
      Files.delete(path)
    }
  }

  "Autosuggest" should "autosuggest perfect match at top" in { suggester =>
    suggester.suggest("camarero", 10).head.text shouldBe "camarero"
  }

  it should "return result sorted by weight" in { suggester =>
    suggester.suggest("camarer", 10).head shouldBe Suggestion("camarera", 100)
  }

  it should "return no more than max number of results" in { suggester =>
    suggester.suggest("camarero", 1).length shouldBe 1
  }
}

