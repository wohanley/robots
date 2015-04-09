package tests.nlp.parsing

import opennlp.tools.parser.Parse
import opennlp.tools.util.Span
import org.specs2.mutable._
import nlp.parsing._


class PackageSpec extends Specification {

  "filter" should {
    "find a single element for a single matching node" in {
      val node = new Parse("", new Span(0, 0), "", 0d, 0)
      filter((x) => true)(Seq())(node) must_=== Seq(node)
    }

    "find nothing in a tree with no matches" in {
      val node = new Parse("", new Span(0, 0), "", 0d, 0)
      filter((x) => false)(Seq())(node) must_=== Seq()
    }

    /* I would love to throw some better tests around this, but God help you
     * if you want to manually build an OpenNLP parse tree. */
  }
}
