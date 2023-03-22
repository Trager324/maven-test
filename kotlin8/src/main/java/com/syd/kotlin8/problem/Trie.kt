package com.syd.kotlin8.problem;

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
//class Trie() {
//    data class TrieNode(
//        var isWord: Boolean = false,
//        var children: Array<TrieNode?> = Array(26) { null }
//    )
//
//    private val root = TrieNode()
//    fun insert(word: String) {
//        var curr = root
//        word.forEach { c ->
//            curr = curr.children[c - 'a'] ?: TrieNode().also { curr.children[c - 'a'] = it }
//        }
//        curr.isWord = true
//    }
//    fun search(word: String): Boolean {
//        var curr = root
//        word.forEach { c -> curr = curr.children[c - 'a'] ?: return false }
//        return curr.isWord
//    }
//    fun startsWith(prefix: String): Boolean {
//        var curr = root
//        prefix.forEach { c -> curr = curr.children[c - 'a'] ?: return false }
//        return true
//    }
//}