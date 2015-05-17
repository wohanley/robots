package com.wohanley.robots.grammar.generative

import com.wohanley.robots.grammar
import com.wohanley.robots.grammar.Grammar
import com.wohanley.robots.grammar.Nonterminal
import com.wohanley.robots.grammar.Production
import com.wohanley.robots.grammar.Terminal
import com.wohanley.robots.util.insertToSet
import scala.language.implicitConversions

class GenerativeGrammar {

  var builtGrammar: Grammar = Map()

  case class ProductionRuleBuilder(symbol: scala.Symbol) {
    def produces(productionChoices: ProductionChoiceBuilder) = {
      productionChoices.productions.foreach { productionBuilder =>
        builtGrammar = insertToSet(Nonterminal(symbol),
          productionBuilder.production,
          builtGrammar)
      }
    }
  }

  case class ProductionChoiceBuilder
    (productions: Set[ProductionBuilder]) {

    def or(nextProductionOption: ProductionBuilder) =
      ProductionChoiceBuilder(productions + nextProductionOption)
  }

  case class ProductionBuilder(production: Production) {
    def andThen(nextProduction: ProductionBuilder) =
      ProductionBuilder(production ++ nextProduction.production)
  }

  implicit def symbolToProductionRuleBuilder = ProductionRuleBuilder

  implicit def productionBuilderToProductionChoiceBuilder
    (productionBuilder: ProductionBuilder) =
    ProductionChoiceBuilder(Set(productionBuilder))
  implicit def symbolToProductionChoiceBuilder(symbol: scala.Symbol) =
    ProductionChoiceBuilder(Set(symbolToProductionBuilder(symbol)))
  implicit def stringToProductionChoiceBuilder(s: String) =
    ProductionChoiceBuilder(Set(stringToProductionBuilder(s)))
  implicit def stringActionToProductionChoiceBuilder(f: () => String) =
    ProductionChoiceBuilder(Set(stringActionToProductionBuilder(f)))

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
