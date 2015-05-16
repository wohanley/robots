package com.wohanley.robots

package object util {

  def upsertToSet[K,V]
    (key: K, value: V, map: Map[K, Set[V]]):
      Map[K, Set[V]] =
    map.get(key) match {
      case None => map + (key -> Set(value))
      case Some(set) => map + (key -> (set + value))
    }
}
