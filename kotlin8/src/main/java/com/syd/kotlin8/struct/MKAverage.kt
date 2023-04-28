package com.syd.kotlin8.struct

import java.util.*

class MKAverage(private val m: Int, private val k: Int) {
    private val pq1 = PriorityQueue<Int>(Comparator.naturalOrder())
    private val pq2 = PriorityQueue<Int>(Comparator.reverseOrder())
    private var sum = 0L
    private var cnt = 0

    fun addElement(num: Int) {
        cnt++
        if (pq1.size < m) {
            pq1.offer(num)
            pq2.offer(num)
            sum -= num
            return
        }
        if (pq1.size == m || num > pq1.peek()) {
            sum += pq1.poll()
            pq1.offer(num)
        }
        if (pq2.size == m || num < pq2.peek()) {
            sum += pq2.poll()
            pq2.offer(num)
        }
    }

    fun calculateMKAverage(): Int {
        if (cnt <= m * 2) {
            return -1
        }
        return (sum / (cnt - m * 2)).toInt()
    }
}
