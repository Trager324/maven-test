package org.behappy.kt.struct

import org.behappy.kt.invokeResults
import java.util.*

/**
 * - 频率最小, 最早使用
 * - 频率查询更新, 位次更新;
 * - 位次: 可查找队列, 能知道谁在谁后面:
 *     - 自增long(可能溢出)
 *     - 使用LinkHash表特性, 记录最小频率元素(开销较大)
 * - [LeetCode Solution](https://leetcode.com/problems/lfu-cache/solutions/2815229/lfu-cache/)
 */
class LFUCache(private val capacity: Int) {
    /**
     * key -> (freq, value)
     */
    private val map = HashMap<Int, Pair<Int, Int>>()

    /**
     * freq -> { key: true }
     */
    private val tree = TreeMap<Int, LinkedHashMap<Int, Boolean>>()

    fun get(key: Int): Int {
        return when (val data = map[key]) {
            null -> -1
            else -> {
                removeMap(key, data.first)
                tree.computeIfAbsent(data.first + 1) { _ -> newAccessOrderMap() }[key] = true
                map[key] = data.first + 1 to data.second
                data.second
            }
        }
    }

    fun put(key: Int, value: Int) {
        map[key]?.apply {
            removeMap(key, first)
            tree.computeIfAbsent(first + 1) { _ -> newAccessOrderMap() }[key] = true
            map[key] = first + 1 to value
            return@put
        }
        if (capacity == map.size) {
            if (tree.isEmpty()) return
            tree.firstEntry().also { (freq, set) ->
                set.entries.first().key.apply {
                    removeMap(this, freq)
                    map.remove(this)
                }
            }
        }
        tree.computeIfAbsent(1) { _ -> newAccessOrderMap() }[key] = true
        map[key] = 1 to value
    }

    private fun <K, V> newAccessOrderMap(): LinkedHashMap<K, V> {
        return LinkedHashMap(16, 0.75f, true)
    }

    private fun removeMap(key: Int, freq: Int) {
        tree[freq]!!.apply {
            remove(key)
            if (isEmpty()) tree.remove(freq)
        }
    }
}

fun main() {
    println(
        invokeResults(
            LFUCache::class,
            "[\"LFUCache\",\"put\",\"put\",\"get\",\"put\",\"get\",\"get\",\"put\",\"get\",\"get\",\"get\"]",
            "[[2],[1,1],[2,2],[1],[3,3],[2],[3],[4,4],[1],[3],[4]]"
        )
    )
    //    println(
    //        invokeResults(
    //            LFUCache::class,
    //            "[\"LFUCache\",\"put\",\"get\"]\n",
    //            "[[0],[0,0],[0]]"
    //        )
    //    )
    //    println(
    //        invokeResults(
    //            LFUCache::class,
    //            "[\"LFUCache\",\"put\",\"put\",\"get\",\"get\",\"get\",\"put\",\"put\",\"get\",\"get\",\"get\",\"get\"]",
    //            "[[3],[2,2],[1,1],[2],[1],[2],[3,3],[4,4],[3],[2],[1],[4]]"
    //        )
    //    )
}
