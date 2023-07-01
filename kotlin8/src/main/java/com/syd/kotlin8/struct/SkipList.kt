package com.syd.kotlin8.struct

import java.util.*

class SkipList<E> {
    internal class Node<E>(val value: E?, level: Int) {
        val next: Array<Node<E>?>

        init {
            val o = arrayOfNulls<Any>(level) as Array<Node<E>?>
            next = o
        }
    }

    private val head = Node<E>(null, 33) // The max. number of levels is 33
    private val rand = Random()
    private var levels = 1
    private val cmp: Comparator<in E>? = null

    /// <summary>
    /// Inserts a value into the skip list.
    /// </summary>
    fun insert(value: E) {
        // Determine the level of the new node. Generate a random number R. The number of
        // 1-bits before we encounter the first 0-bit is the level of the node. Since R is
        // 32-bit, the level can be at most 32.
        var level = 0
        var r = rand.nextInt()
        while (r and 1 == 1) {
            level++
            if (level == levels) {
                levels++
                break
            }
            r = r shr 1
        }

        // Insert this node into the skip list
        val newNode = Node(value, level + 1)
        var cur: Node<E>? = head
        for (i in levels - 1 downTo 0) {
            while (cur!!.next[i] != null) {
                if (cmp!!.compare(cur.next[i]!!.value, value) > 0) break
                cur = cur.next[i]
            }
            if (i <= level) {
                newNode.next[i] = cur.next[i]
                cur.next[i] = newNode
            }
        }
    }

    /// <summary>
    /// Returns whether a particular value already exists in the skip list
    /// </summary>
    operator fun contains(value: E): Boolean {
        var cur: Node<E>? = head
        for (i in levels - 1 downTo 0) {
            while (cur!!.next[i] != null) {
                if (cmp!!.compare(cur.next[i]!!.value, value) > 0) break
                if (cur.next[i]!!.value === value) return true
                cur = cur.next[i]
            }
        }
        return false
    }

    /// <summary>
    /// Attempts to remove one occurence of a particular value from the skip list. Returns
    /// whether the value was found in the skip list.
    /// </summary>
    fun remove(value: E): Boolean {
        var cur: Node<E>? = head
        var found = false
        for (i in levels - 1 downTo 0) {
            while (cur!!.next[i] != null) {
                if (cur.next[i]!!.value === value) {
                    found = true
                    cur.next[i] = cur.next[i]!!.next[i]
                    break
                }
                if (cmp!!.compare(cur.next[i]!!.value, value) > 0) break
                cur = cur.next[i]
            }
        }
        return found
    }

    /// <summary>
    /// Produces an enumerator that iterates over elements in the skip list in order.
    /// </summary>
    operator fun iterator(): Iterator<E> {
        return object : MutableIterator<E> {
            var cur = head.next[0]
            override fun hasNext(): Boolean {
                return cur != null
            }

            override fun next(): E {
                val res = cur!!.value
                cur = cur!!.next[0]
                return res!!
            }

            override fun remove() {
                TODO("Not yet implemented")
            }
        }
    }
}