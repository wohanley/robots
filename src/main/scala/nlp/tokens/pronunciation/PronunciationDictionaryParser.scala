package nlp.tokens.pronunciation

import scala.util.parsing.combinator._


/** The syllabified CMU pronouncing dictionary, found at
  * http://webdocs.cs.ualberta.ca/~kondrak/cmudict/cmudict.rep, contains rows
  * formatted like:
  * ABATEMENT  AH0 - B EY1 T - M AH0 N T
  * This object can parse those rows. */
object PronunciationDictionaryParser extends RegexParsers {

  def word: Parser[String] = """[a-zA-Z]+""".r ^^ { _.toString }

  /** Sometimes a word has multiple pronuncations, indicated by a number in
    * parentheses to the right. For example:
    * ABBE  AE1 - B IY0
    * ABBE(2)  AE0 - B EY1 */
  def altNumber: Parser[Int] = """\(""".r ~ """[0-9]+""".r  ~ """\)""".r ^^ {
    case _ ~ num ~ _ => Integer.parseInt(num)
  }

  def key: Parser[nlp.tokens.Word] = word <~ opt(altNumber)

  /** Phonemes are sometimes followed by a number indicating, I think, stress
    * patterns? w/e I don't care, so just ignore it */
  def phoneme: Parser[Phoneme] = """[A-Z]+""".r <~ opt("""[0-9]+""".r) ^^ {
    Symbol(_)
  }

  def syllable: Parser[Syllable] = rep(phoneme)

  def pronunciation: Parser[Pronunciation] = repsep(syllable, "-")

  def entry: Parser[(Word, Pronunciation)] = word ~ pronunciation ^^ {
    case w ~ p => (w, p)
  }
}
