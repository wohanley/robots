package tests.nlp

import org.specs2.mutable._
import nlp._

class PackageSpec extends Specification {

  "isVowel" should {
    "return true for vowel phonemes" in {
      !(Set('AH, 'EY, 'ER, 'IY)
        .map(phoneme => isVowel(phoneme))
        .contains(false))
    }
    "return false for consonant phonemes" in {
      !(Set('B, 'D, 'L, 'N, 'TH)
        .map(phoneme => isVowel(phoneme))
        .contains(true))
    }
    "return false for unknown phonemes" in {
      !isVowel('KITTIES)
    }
  }

  "isRhyme" should {
    "return true when syllables rhyme" in {
      isRhyme(Seq('K, 'AH, 'T), Seq('SH, 'AH, 'T))
      isRhyme(Seq('S, 'N, 'AA, 'T, 'CH), Seq('K, 'AA, 'T, 'CH))
    }

    "return false when syllables don't rhyme" in {
      !isRhyme(Seq('M, 'AE, 'TH), Seq('M, 'OW, 'TH))
      !isRhyme(Seq('T, 'R, 'UW, 'TH), Seq('M, 'AW, 'TH))
    }
  }

  "mergeTokens" should {
    "combine split contractions" in {
      Seq("I'd", "never") == mergeTokens(Seq("I", "'d", "never"))
    }
  }
}
