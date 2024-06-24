package com.behappy.kt.struct

import com.behappy.kt.invokeResults

class LRUCache(private val capacity: Int) {
    private val map = LinkedHashMap<Int, Int>(capacity.shl(1), 0.75f, true)
    fun get(key: Int) = map.getOrDefault(key, -1)
    fun put(key: Int, value: Int) {
        if (!map.containsKey(key) && map.size >= capacity) map.remove(map.entries.first().key)
        else map[key] = value
    }
}

fun main() {
    println(
        invokeResults(
            LRUCache::class,
            "[\"LRUCache\",\"put\",\"put\",\"get\",\"put\",\"get\",\"put\",\"get\",\"get\",\"get\"]",
            "[[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]"
        )
    )
}
