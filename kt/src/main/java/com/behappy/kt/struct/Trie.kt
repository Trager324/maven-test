package com.behappy.kt.struct

class Trie() {
    private val children = HashMap<Char, Trie>()
    private var isLeaf = false
    fun insert(word: String, idx: Int = 0): Unit =
        if (word.length == idx) isLeaf = true
        else children.computeIfAbsent(word[idx]) { _ -> Trie() }.insert(word, idx + 1)
    fun search(word: String, idx: Int = 0, pre: Boolean = false): Boolean =
        if (idx == word.length) pre || isLeaf else children[word[idx]]?.search(word, idx + 1, pre) ?: false
    fun startsWith(prefix: String, idx: Int = 0): Boolean = search(prefix, 0, true)
}