package org.behappy.kt

interface IEnum {

    companion object {
        inline fun <reified E : Enum<E>> contains(name: String): Boolean {
            return try {
                enumValueOf<E>(name)
                true
            } catch (_: Exception) {
                false
            }
        }
    }
}
