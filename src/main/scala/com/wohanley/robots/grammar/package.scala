package com.wohanley.robots

package object grammar {

  /** Be very careful with this trait! Don't confuse it with [[scala.Symbol]].
    * I don't recommend importing this - qualify it for clarity. */
  trait Symbol
  type Production = Seq[grammar.Symbol]
  case class Nonterminal(name: scala.Symbol) extends grammar.Symbol
  case class Terminal(value: String) extends grammar.Symbol

  type Grammar = Map[Nonterminal, Set[Production]]
}
