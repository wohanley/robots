package tests.grammar.generative

import com.wohanley.robots.grammar.Grammar
import com.wohanley.robots.grammar.generative.GenerativeGrammar
import org.specs2.mutable._


class PackageSpec extends Specification {
  object Test extends GenerativeGrammar {
    'start produces ('greeting then ", " then 'body)
  }
}
