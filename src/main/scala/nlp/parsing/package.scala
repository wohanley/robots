package nlp

/** Tools for dealing with parse trees. */
package object parsing {

  import opennlp.tools.parser.Parse


  /** Search through a parse tree for noun phrases. */
  def nounPhrases(nps: Seq[Parse], node: Parse): Seq[Parse] = {

    val npsWithNode: Seq[Parse] = node.getType() match {
      case "NP" => nps :+ node
      case _ => nps
    }

    // flatten does maintain order, as far as I can tell
    npsWithNode ++ node.getChildren()
      .map(p => nounPhrases(nps, p)).flatten.toSeq
  }
}
