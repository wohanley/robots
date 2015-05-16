package com.wohanley.robots.grammar.generative

package object dsl {

  import com.wohanley.robots.grammar
  import com.wohanley.robots.grammar.Nonterminal

  implicit def ScalaSymbolToNonterminal(scalaSymbol: scala.Symbol):
      grammar.Symbol = Nonterminal(scalaSymbol)
}
