package com.wohanley.robots.nlp.tokens

package object pronunciation {

  import java.io.File
  import java.util.Scanner
  import opennlp.tools.tokenize.Tokenizer
  import scala.collection.immutable.HashMap
  import scala.util.parsing.combinator._


  type Phoneme = Symbol
  type Syllable = Seq[Phoneme]
  type Pronunciation = Seq[Syllable]
  type PronunciationDictionary = Map[String, Set[Pronunciation]]

  private def vowelPhonemes = Set('AA, 'AH, 'AW, 'EH, 'ER, 'EY, 'IH, 'OW , 'UH, 'AE, 'AO, 'AY, 'IY, 'OY, 'UW)

  /** A phoneme can be either a vowel sound or a consonant sound. */
  def isVowel(phoneme: Phoneme): Boolean =
    vowelPhonemes.contains(phoneme)

  def isRhyme(word1: Pronunciation, word2: Pronunciation): Boolean =
    reducedRhyme(word1) match { // I wonder if there's a cleaner way to do this
      case None => false
      case Some(rhyme1) => reducedRhyme(word2) match {
        case None => false
        case Some(rhyme2) => rhyme2 == rhyme1
      }
    }

  /** Two words rhyme if they are the same from the first vowel phoneme
    * onward. This function chops off that portion of a word, for comparison to
    * other words (likely in [[isRhyme]]). */
  def reducedRhyme(word: Pronunciation): Option[Pronunciation] =
    word match {
      case Nil => None
      case head +: rest => Some(fromFirstVowel(head) +: rest)
    }

  def fromFirstVowel(syllable: Syllable): Syllable =
    syllable.slice(syllable.indexWhere(isVowel), syllable.length)

  def syllabify
    (pronunciations: PronunciationDictionary)
    (tokens: Seq[String]): Seq[Option[Pronunciation]] = {
    mergeTokens(tokens)
      .map { token =>
        pronunciations.getOrElse(token.toUpperCase(), Set())
    }.map(_.headOption)
  }

  def pronunciationsFromFile(fileName: String): PronunciationDictionary = {
    val parser = PronunciationDictionaryParser // typing convenience
    var dictionary = new HashMap[String, Set[Pronunciation]]

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
