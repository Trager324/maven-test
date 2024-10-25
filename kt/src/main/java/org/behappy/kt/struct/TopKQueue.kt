package org.behappy.kt.struct

import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.Comparator

class TopKQueue<E> : AbstractQueue<E> {
    override val size: Int = 0

    val k: Int
    private val map: MutableMap<E, Int>

    constructor(k: Int) : this(k, null)

    constructor(k: Int, comparator: Comparator<E>?) : super() {
        this.k = k
        this.map = TreeMap(comparator)
    }

    override fun iterator(): MutableIterator<E> {
        TODO("Not yet implemented")
    }

    override fun poll(): E {
        TODO("Not yet implemented")
    }

    override fun peek(): E {
        TODO("Not yet implemented")
    }

    override fun offer(e: E): Boolean {
        TODO("Not yet implemented")
    }
}

fun main() {
    val topK = TopKQueue<Int>(10)
    for (i in 0 until 20) {
        val v = ThreadLocalRandom.current().nextInt(0, 1000)
        println(v)
        topK.add(v)
    }
    println(topK)
}
