package com.syd.kotlin8.problem

import java.util.*

class SnapshotArray(length: Int) {
    private val versionMap = Array(length) { TreeMap<Int, Int>().apply { put(0, 0) } }
    private var snapId = 0
    fun set(index: Int, `val`: Int) {
        versionMap[index][snapId] = `val`
    }

    fun snap(): Int {
        return snapId++
    }

    fun get(index: Int, snap_id: Int): Int {
        return versionMap[index].floorEntry(snap_id).value
    }
}
