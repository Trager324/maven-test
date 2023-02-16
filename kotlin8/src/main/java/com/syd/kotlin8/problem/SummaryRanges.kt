package com.syd.kotlin8.problem

import com.alibaba.fastjson.JSON
import com.syd.kotlin8.invokeResults
import java.util.*


class SummaryRanges() {
    private val map = TreeMap<Int, Int>()
    fun addNum(value: Int) {
        var k = value
        var v = value
        map.floorEntry(value)?.let { (k0, v0) ->
            when (value) {
                in k0..v0 -> return@addNum
                v0 + 1 -> k = k0
            }
        }
        if (map.contains(value + 1)) v = map.remove(value + 1)!!
        map[k] = v
    }

    fun getIntervals(): Array<IntArray> = map.map { intArrayOf(it.key, it.value) }.toTypedArray()
}

fun main() {
    println(JSON.parse(JSON.toJSONBytes(
        invokeResults(SummaryRanges::class,
            "[\"SummaryRanges\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\"]",
            "[[],[6],[],[6],[],[0],[],[4],[],[8],[],[7],[],[6],[],[4],[],[7],[],[5],[]]"))))
}

/**
 * Your SummaryRanges object will be instantiated and called as such:
 * var obj = SummaryRanges()
 * obj.addNum(value)
 * var param_2 = obj.getIntervals()
 */