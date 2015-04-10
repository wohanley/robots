package com.wohanley.robots.nlp

/** Tools for dealing with parse trees. */
package object parsing {

  import com.wohanley.robots.nlp.tokens._
  import java.io.FileInputStream
  import opennlp.tools.parser.AbstractBottomUpParser
  import opennlp.tools.parser.Parse
  import opennlp.tools.parser.ParserFactory
  import opennlp.tools.parser.ParserModel
  import opennlp.tools.util.Span


  lazy val englishParser = ParserFactory.create(
    new ParserModel(
      new FileInputStream("en-parser-chunking.bin")))

  /* Lifted from http://blog.dpdearing.com/2011/12/
   * how-to-use-the-opennlp-1-5-0-parser/ */
  def parse(text: String): Parse = {
    val p = new Parse(text,
      new Span(0, text.length()), AbstractBottomUpParser.INC_NODE, 1, 0)

    val spans = englishTokenizer.tokenizePos(text);

    spans.zipWithIndex.foreach { case (span, index) =>
      // flesh out the parse with individual token sub-parses 
      p.insert(new Parse(text, span, AbstractBottomUpParser.TOK_NODE, 0, index))
    }

    englishParser.parse(p);
  }

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
