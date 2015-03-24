package tests.nlp

import org.specs2.mutable._
import nlp.tokens.pronunciation._

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
    "return true when words rhyme" in {
      isRhyme(Seq(Seq('K, 'AH, 'T)), Seq(Seq('SH, 'AH, 'T)))
      isRhyme(Seq(Seq('S, 'N, 'AA, 'T, 'CH)), Seq(Seq('K, 'AA, 'T, 'CH)))
      isRhyme(
        Seq(Seq('S, 'K, 'W, 'AA), Seq('T, 'ER)),
        Seq(Seq('P, 'AA), Seq('T, 'ER)))
    }

    "return false when words don't rhyme" in {
      !isRhyme(Seq(Seq('M, 'AE, 'TH)), Seq(Seq('M, 'OW, 'TH)))
      !isRhyme(Seq(Seq('T, 'R, 'UW, 'TH)), Seq(Seq('M, 'AW, 'TH)))
    }
  }
}
