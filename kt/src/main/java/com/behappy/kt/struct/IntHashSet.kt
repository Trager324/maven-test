package com.behappy.kt.struct


class IntHashSet @JvmOverloads constructor(threshold: Int = 16) {
    private val loadFactor = 0.75f
    private var values: Array<Node?>
    private var threshold = 16
    private var size = 0

    init {
        val n = -1 ushr Integer.numberOfLeadingZeros(threshold - 1)
        this.threshold = if (n < 0) 1 else if (n >= MAXIMUM_CAPACITY) MAXIMUM_CAPACITY else n + 1
        values = arrayOfNulls(this.threshold)
    }

    private fun resize(newThreshold: Int) {
        val newValues = arrayOfNulls<Node>(newThreshold)
        val mod = newThreshold - 1
        for (value in values) {
            var node = value
            while (node != null) {
                val tmp = node.next
                val newKey = node.`val` and mod
                if (newValues[newKey] == null) {
                    newValues[newKey] = node
                } else {
                    var n = newValues[newKey]
                    while (n!!.next != null) {
                        n = n.next
                    }
                    n.next = node
                }
                node.next = null
                node = tmp
            }
        }
        values = newValues
        threshold = newThreshold
    }

    fun add(key: Int) {
        val idx = key and threshold - 1
        var n = values[idx]
        if (n == null) {
            n = Node(key)
            values[idx] = n
        } else {
            if (n.`val` == key) return
            while (n!!.next != null) {
                if (n.next!!.`val` == key) return
                n = n.next
            }
            n.next = Node(key)
        }
        if (++size > threshold * loadFactor) {
            resize(threshold shl 1)
        }
    }

    fun remove(key: Int) {
        val idx = key and threshold - 1
        val tmp = Node(0, values[idx])
        var n: Node? = tmp
        while (n!!.next != null) {
            if (n.next!!.`val` == key) {
                n.next = n.next!!.next
                break
            }
            n = n.next
        }
        values[idx] = tmp.next
    }

    operator fun contains(key: Int): Boolean {
        val idx = key and threshold - 1
        var n = values[idx]
        while (n != null) {
            if (n.`val` == key) {
                return true
            }
            n = n.next
        }
        return false
    }

    fun size(): Int {
        return size
    }

    private class Node {
        var `val` = 0
        var next: Node? = null

        constructor(v: Int) {
            `val` = v
        }

        constructor(v: Int, n: Node?) {
            `val` = v
            next = n
        }
    }

    companion object {
        private const val MAXIMUM_CAPACITY = 1 shl 30
        @JvmStatic
        fun main(args: Array<String>) {
            val set = IntHashSet()
            set.add(1)
            set.add(2)
            println(set.contains(1))
            println(set.contains(3))
            set.add(2)
            println(set.contains(2))
            set.remove(2)
            println(set.contains(2))
        }
    }
}



