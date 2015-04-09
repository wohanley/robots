package com.wohanley.robots.nlp

package object tokens {

  import java.io.FileInputStream
  import opennlp.tools.tokenize.Tokenizer
  import opennlp.tools.tokenize.TokenizerME
  import opennlp.tools.tokenize.TokenizerModel


  lazy val englishTokenizer =
    new TokenizerME(
      new TokenizerModel(
        new FileInputStream("en-token.bin")))


  def appendToken(tokens: Seq[String], token: String): Seq[String] = {

    val backwardMergingTokens = """('.*)|(n't)|[!?.,:]|(st)|(nd)|(rd)|(th)""".r
    val secondRequiresMerge = token match {
      case backwardMergingTokens(_*) => true
      case _ => false
    }
    val firstRequiresMerge = tokens.lastOption match {
      case Some(previousToken) => previousToken == "#"
      case None => false
    }

    if (secondRequiresMerge) {
      tokens.lastOption match {
        case Some(previousToken) =>
          tokens.dropRight(1) :+ (previousToken + token)
        case None => Seq(token)
      }
    } else if (firstRequiresMerge) {
      tokens.dropRight(1) :+ (tokens.head + token)
    } else {
      tokens :+ token
    }
  }

  def mergeTokens(tokens: Seq[String]): Seq[String] =
    tokens.foldLeft(Seq[String]())(appendToken)
}
