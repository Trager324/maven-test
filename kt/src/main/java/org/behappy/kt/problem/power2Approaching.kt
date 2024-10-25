package org.behappy.kt.problem

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

/**
 * power 2 approaching power 10
 */
fun main() {
    var v2 = BigInteger.TWO
    var v10 = BigInteger.TEN
    val v100 = BigDecimal.valueOf(100)
    var x2 = 1
    var x10 = 1
    for (i in 0 until 100) {
        var nv2 = v2.multiply(BigInteger.TWO)
        while (nv2.minus(v10).abs() < v2.minus(v10).abs()) {
            v2 = nv2
            x2++
            nv2 = nv2.multiply(BigInteger.TWO)
        }
        val rate = v2.minus(v10).abs().toBigDecimal()
            .divide(v10.toBigDecimal(), 6, RoundingMode.HALF_UP)
            .multiply(v100)
        System.out.printf("|10^%d = %s - 2^%d = %s| = %s%%\n", x10, v10, x2, v2, rate)
        v10 = v10.multiply(BigInteger.TEN)
        v2 = nv2
        x2++
        x10++
    }
}

/**
 * Your SummaryRanges object will be instantiated and called as such:
 * var obj = SummaryRanges()
 * obj.addNum(value)
 * var param_2 = obj.getIntervals()
 */