package nlp

/** Tools for dealing with parse trees. */
package object parsing {

  import opennlp.tools.parser.Parse


  /** Search through a parse tree for nodes that satisfy a predicate. */
  def filter(f: Parse => Boolean)(found: Seq[Parse])(node: Parse):
      Seq[Parse] = {

    val foundAfterNode: Seq[Parse] = if (f(node)) {
      found :+ node
    } else {
      found
    }

    node.getChildren().foldLeft(foundAfterNode) {
      (found, node) => filter(f)(found)(node)
    }
  }
}
