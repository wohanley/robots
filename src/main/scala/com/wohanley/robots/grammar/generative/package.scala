package com.wohanley.robots.grammar

package object generative {

  import com.wohanley.robots.grammar
  import com.wohanley.robots.grammar.Grammar
  import com.wohanley.robots.grammar.Nonterminal
  import com.wohanley.robots.grammar.Production
  import com.wohanley.robots.grammar.Terminal

  case class Action(f: () => Production) extends grammar.Symbol


  def takeRandom[T](xs: Traversable[T]): Option[T] =
    /** toVector is necessary here in case a Set gets passed in. For some
      * reason shuffle is defined on Traversable (as opposed to Iterable)?
      * I'm not sure that makes any sense. *shrug* */
    util.Random.shuffle(xs.toVector).headOption

  def produceRandom(grammar: Grammar, left: Nonterminal): Option[Production] =
    grammar.get(left).flatMap(productions => takeRandom(productions))

  def reduceProduction(grammar: Grammar, production: Production): Seq[String] =
    (for (symbol <- production) yield reduceSymbol(grammar, symbol)).flatten

  def reduceSymbol(grammarIn: Grammar, symbol: grammar.Symbol): Seq[String] =
    reduceSymbol(Seq.empty[String], grammarIn, symbol)

  def reduceSymbol(result: Seq[String], grammarIn: Grammar, symbol: grammar.Symbol):
      Seq[String] =
    symbol match {
      case Terminal(value) => result :+ value
      case Nonterminal(name) =>
        produceRandom(grammarIn, Nonterminal(name)) match {
          case None => Seq.empty[String]
          case Some(production) => reduceProduction(grammarIn, production)
        }
      case Action(f) => reduceProduction(grammarIn, f())
    }

  def start(grammar: Grammar): Option[Production] =
    produceRandom(grammar, Nonterminal('start))

  /** Randomly generate a text that conforms to the given grammar. */
  def randomText(grammar: Grammar): Option[String] = {
    produceRandom(grammar, Nonterminal('start)).map(production =>
      reduceProduction(grammar, production).mkString
    )
  }
}
