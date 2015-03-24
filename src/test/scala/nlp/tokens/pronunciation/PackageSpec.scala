package tests.nlp

import org.specs2.mutable._
import nlp.tokens.pronunciation._

class PackageSpec extends Specification {

  "isVowel" should {
    "return true for vowel phonemes" in {
      !(Set('AH, 'EY, 'ER, 'IY)
        .map(phoneme => isVowel(phoneme))
        .contains(false)) must_== true
    }
    "return false for consonant phonemes" in {
      !(Set('B, 'D, 'L, 'N, 'TH)
        .map(phoneme => isVowel(phoneme))
        .contains(true)) must_== true
    }
    "return false for unknown phonemes" in {
      isVowel('KITTIES) must_== false
    }
  }

  "isRhyme" should {
    "return true when words rhyme" in {
      isRhyme(Seq(Seq('K, 'AH, 'T)), Seq(Seq('SH, 'AH, 'T))) must_== true
      isRhyme(
        Seq(Seq('S, 'N, 'AA, 'T, 'CH)),
        Seq(Seq('K, 'AA, 'T, 'CH))) must_== true
      isRhyme(
        Seq(Seq('S, 'K, 'W, 'AA), Seq('T, 'ER)),
        Seq(Seq('P, 'AA), Seq('T, 'ER))) must_== true
    }

    "return false when words don't rhyme" in {
      isRhyme(Seq(Seq('M, 'AE, 'TH)), Seq(Seq('M, 'OW, 'TH))) must_== false
      isRhyme(Seq(Seq('T, 'R, 'UW, 'TH)), Seq(Seq('M, 'AW, 'TH))) must_== false
    }
  }

  "reducedRhyme" should {
    "return a word from the first vowel phoneme on" in {
      reducedRhyme(Seq(Seq('B, 'AH), Seq('T, 'ER))) must_==
        Some(Seq(Seq('AH), Seq('T, 'ER)))
    }
  }
}
