package com.behappy.kt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.SimpleEvaluationContext
import org.springframework.expression.spel.support.StandardEvaluationContext
import kotlin.reflect.jvm.javaMethod

fun reverseString(input: String): String {
    return input.reversed()
}

internal class TemplateUtilsTest {
    private var parser: ExpressionParser = SpelExpressionParser()
    private var context = StandardEvaluationContext()

    @Test
    fun testHelloWorld() {
        val parser = SpelExpressionParser()
        val exp = parser.parseExpression("'Hello World'.concat('!')")
        val message = exp.value as String
        println(message)
        val i = 0
        val s = "$i"
    }

    @Test
    fun testSpringEL() {
        val parser = SpelExpressionParser()

        val context = SimpleEvaluationContext.forReadOnlyDataBinding().build()
        context.setVariable("reverseString", ::reverseString.javaMethod)
        val clz = TemplateUtilsTest::class.java


        val helloWorldReversed = parser.parseExpression(
            "#reverseString('hello')"
        ).getValue(context, String::class.java)
        val helloWorldReversed2 = parser.parseExpression(
            "#reverseString2('hello')"
        ).getValue(context, String::class.java)
        assertEquals("olleh", helloWorldReversed)

        //        val context = SimpleEvaluationContext.forReadOnlyDataBinding().build()
        //        context.setVariable("reverseString", ::reverseString::javaMethod)
        //
        //        val helloWorldReversed = parser.parseExpression(
        //            "#reverseString('hello')").getValue(context, String::class.java)
    }

    companion object {
        fun isMember(s: String) = true
    }
}