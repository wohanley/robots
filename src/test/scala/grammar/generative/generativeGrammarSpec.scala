package tests.grammar.generative

import com.wohanley.robots.grammar.Grammar
import com.wohanley.robots.grammar.Nonterminal
import com.wohanley.robots.grammar.generative._
import org.specs2.mutable._


class GenerativeGrammarSpec extends Specification {

  "DSL" should {
    "cope with hello world" in {
      object HelloWorld extends GenerativeGrammar {
        'start      produces ('greeting then ", " then 'body)
        'greeting   produces "Hello"
        'body       produces "World"
      }

      randomText(HelloWorld.builtGrammar) must_=== Some("Hello, World")
    }
  }
}
