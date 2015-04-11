package com.wohanley.robots.nlp

/** Tools for dealing with parse trees. */
package object parsing {

  import com.wohanley.robots.nlp.tokens._
  import java.io.FileInputStream
  import opennlp.tools.parser.AbstractBottomUpParser
  import opennlp.tools.parser.Parse
  import opennlp.tools.parser.Parser
  import opennlp.tools.parser.ParserFactory
  import opennlp.tools.parser.ParserModel
  import opennlp.tools.tokenize.Tokenizer
  import opennlp.tools.util.Span


  lazy val englishParser = ParserFactory.create(
    new ParserModel(
      new FileInputStream("en-parser-chunking.bin")))

  /** Returns a parse tree for the given text. */
  def parse(tokenizer: Tokenizer)(parser: Parser)(text: String): Parse = {
    /* Lifted from http://blog.dpdearing.com/2011/12/
     * how-to-use-the-opennlp-1-5-0-parser/ */
    val spans = tokenizer.tokenizePos(text);

    val p = new Parse(text,
      new Span(0, text.length()), AbstractBottomUpParser.INC_NODE, 1, 0)

    spans.zipWithIndex.foreach { case (span, index) =>
      // flesh out the parse with individual token sub-parses 
      p.insert(new Parse(text, span, AbstractBottomUpParser.TOK_NODE, 0, index))
    }

    parser.parse(p);
  }

  /** Search through a parse tree for nodes that satisfy a predicate. */
  def filter(f: Parse => Boolean)(found: Seq[Parse])(node: Parse):
      Seq[Parse] = {
    /* I'd like to revisit this and try to find a way to wrap an OpenNLP parse
     * tree with an actual tree class, maybe Scalaz's Tree, but I can't figure
     * out how Scalaz's Tree works right now at all */
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
