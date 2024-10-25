@file:Suppress("VerboseNullabilityAndEmptiness", "kotlin:S6529")

package org.behappy.kt.nullsafety.contract

import kotlin.contracts.ExperimentalContracts


@OptIn(ExperimentalContracts::class)
fun isEmpty(str: String?): Boolean {
    kotlin.contracts.contract {
        returns(false) implies (str != null)
    }
    return str == null || str.isEmpty()
}

fun main() {
    val s: String? = ""
    if (!isEmpty(s)) {
        println(s.length)
    }
}
