package nlp.tokens

package object pronunciation {

  import java.io.File
  import java.util.Scanner
  import opennlp.tools.tokenize.Tokenizer
  import scala.collection.immutable.HashMap
  import scala.util.parsing.combinator._


  type Phoneme = Symbol
  type Syllable = Seq[Phoneme]
  type Pronunciation = Seq[Syllable]
  type Word = String
  type PronunciationDictionary = Map[Word, Set[Pronunciation]]

  private def vowelPhonemes = Set('AA, 'AH, 'AW, 'EH, 'ER, 'EY, 'IH, 'OW , 'UH, 'AE, 'AO, 'AY, 'IY, 'OY, 'UW)

  /** A phoneme can be either a vowel sound or a consonant sound. */
  def isVowel(phoneme: Phoneme): Boolean =
    vowelPhonemes.contains(phoneme)

  /** Two syllables rhyme if they are the same from the first vowel phoneme
    * onward. */
  def isRhyme(syll1: Syllable, syll2: Syllable): Boolean =
    syll2.endsWith(rhymeSyllable(syll1))

  def rhymeSyllable(syllable: Syllable): Syllable =
    syllable.slice(syllable.indexWhere(isVowel), syllable.length)

  def syllabify(tokenizer: Tokenizer)(text: String):
      Seq[Option[Pronunciation]] = syllabify(tokenizer.tokenize(text))

  def syllabify(tokens: Seq[String]): Seq[Option[Pronunciation]] = {
    mergeTokens(tokens)
      .map { token =>
        pronunciationsFromFile("cmudict").getOrElse(token.toUpperCase(), Set())
    }.map(_.headOption)
  }

  def pronunciationsFromFile(fileName: String): PronunciationDictionary = {
    val parser = PronunciationDictionaryParser // typing convenience
    var dictionary = new HashMap[Word, Set[Pronunciation]]

    val scanner = new Scanner(new File(fileName))
    while (scanner.hasNextLine) {
      val line = scanner.nextLine
      if (!line.startsWith("##")) {
        parser.parse(parser.entry, line) match {
          case parser.Success((word, pronunciation), _) =>
            dictionary.get(word) match {
              case Some(prons) =>
                dictionary = dictionary + (word -> (prons + pronunciation))
              case None =>
                dictionary = dictionary + (word -> Set(pronunciation))
            }
          case parser.Error(message, _) => println(message)
          case parser.Failure(message, _) => println (message)
        }
      }
    }

    dictionary
  }
}
