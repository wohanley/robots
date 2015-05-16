package tests.util

import com.wohanley.robots.util.insertToSet
import org.specs2.mutable._


class PackageSpec extends Specification {

  "insertToSet for Map" should {
    "insert element with new key" in {
      insertToSet("a", "test", Map[String, Set[String]]()) must_===
        Map("a" -> Set("test"))
    }

    "insert to existing set" in {
      insertToSet("a", "test2", Map("a" -> Set("test"))) must_===
        Map("a" -> Set("test", "test2"))
    }
  }
}
