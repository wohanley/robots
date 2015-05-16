package com.wohanley.robots.grammar.generative

import com.wohanley.robots.grammar
import com.wohanley.robots.grammar.Grammar
import com.wohanley.robots.grammar.Nonterminal
import com.wohanley.robots.grammar.Production
import com.wohanley.robots.grammar.Terminal
import com.wohanley.robots.util.upsertToSet


class GenerativeGrammar {

  var builtGrammar: Grammar = Map()

  case class ProductionRuleBuilder(symbol: scala.Symbol) {

    def produces(productionBuilder: ProductionBuilder) = {
      builtGrammar = upsertToSet(Nonterminal(symbol),
        productionBuilder.production,
        builtGrammar)
    }
  }

  case class ProductionBuilder(production: Production) {

    def then(nextProduction: ProductionBuilder) =
      production ++ nextProduction.production
  }

  implicit def symbolToProductionRuleBuilder = ProductionRuleBuilder

  implicit def productionToProductionBuilder = ProductionBuilder
  implicit def symbolToProductionBuilder(symbol: scala.Symbol) =
    ProductionBuilder(Seq(Nonterminal(symbol)))
  implicit def stringToProductionBuilder(s: String) =
    ProductionBuilder(Seq(Terminal(s)))
  implicit def stringActionToProductionBuilder(f: () => String) =
    ProductionBuilder(Seq(Action { () =>
      Seq(Terminal(f()))
    }))
}
