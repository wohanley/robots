package tests.grammar.generative.dsl

import com.wohanley.robots.grammar.generative.dsl._
import org.specs2.mutable._


class PackageSpec extends Specification {
  val test = {
    'start -> 'greeting ", " 'body
  }
}
