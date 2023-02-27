package com.syd.kotlin8


import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONArray
import com.alibaba.fastjson2.JSONFactory
import com.alibaba.fastjson2.JSONObject
import com.syd.algo.leetcode.ListNode.parseListNode
import com.syd.algo.leetcode.TreeNode.parseTreeNode
import jdk.dynalink.linker.support.TypeUtilities
import kotlinx.coroutines.*
import lombok.*
import lombok.experimental.Accessors
import lombok.extern.java.Log
import java.io.*
import java.lang.annotation.*
import java.lang.annotation.Repeatable
import java.lang.constant.Constable
import java.lang.reflect.*
import java.lang.reflect.Proxy
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import java.net.*
import java.nio.file.*
import java.time.*
import java.util.*
import java.util.concurrent.*
import java.util.function.*
import java.util.function.Function
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.stream.*
import java.util.stream.Collectors.*
import kotlin.Any
import kotlin.Array
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.IntArray
import kotlin.Long
import kotlin.String
import kotlin.Throws
import kotlin.also
import kotlin.arrayOfNulls
import kotlin.isNaN
import kotlin.reflect.KClass
import kotlin.require

class Node(var `val`: Boolean, var isLeaf: Boolean) {
    var topLeft: Node? = null
    var topRight: Node? = null
    var bottomLeft: Node? = null
    var bottomRight: Node? = null
}

class Solution {
    private fun Int.toBool(): Boolean = this != 0

    private fun slice(grid: Array<IntArray>, rr: IntRange, cr: IntRange): Array<IntArray> {
        return grid.sliceArray(rr)
            .map { ia -> ia.sliceArray(cr) }
            .toTypedArray()
    }

    fun construct(grid: Array<IntArray>): Node? {
        val n = grid.size
        if (n == 1) return Node(grid[0][0] != 0, true)
        val tl = construct(slice(grid, 0 until n / 2, 0 until n / 2))!!
        val tr = construct(slice(grid, 0 until n / 2, n / 2 until n))!!
        val bl = construct(slice(grid, n / 2 until n, 0 until n / 2))!!
        val br = construct(slice(grid, n / 2 until n, n / 2 until n))!!
        return if (tl.isLeaf && tr.isLeaf && bl.isLeaf && br.isLeaf &&
            tl.`val` == tr.`val` && tl.`val` == bl.`val` && tl.`val` == br.`val`)
            Node(tl.`val`, true) else Node(false, false).apply {
                bottomLeft = bl; bottomRight = br; topLeft = tl; topRight = tr;
        }
    }
}

fun main() {
    println(solution.construct(parseIntMatrix("[[1,1,0,0],[0,0,1,1],[1,1,0,0],[0,0,1,1]]")))
}

val solution = Solution()

fun <T : Any> parseObject(text: String?, clazz: KClass<T>): T {
    return JSON.parseObject(text, clazz.java)
}

fun parseIntArray(str: String?): IntArray {
    return parseObject(str, IntArray::class)
}

fun parseIntMatrix(str: String?): Array<IntArray> {
    return parseObject(str, Array<IntArray>::class)
}

fun parseStringArray(str: String?): Array<String> {
    return parseObject(str, Array<String>::class)
}

@Throws(NoSuchMethodException::class)
fun getExecutableArray(clazz: Class<*>, methodNames: Array<String>, argsArray: Array<Array<Any?>>): Array<Executable> {
    val n = methodNames.size
    val methods = clazz.declaredMethods
    val ctors = clazz.declaredConstructors
    val clazzName = clazz.simpleName
    return (0 until n).map<Int, Executable> {
        val methodName = methodNames[it]
        val argArray = argsArray[it]
        if (clazzName == methodName) {
            for (ctor in ctors) if (isMatchedExecutable(ctor, argArray)) {
                ctor.isAccessible = true
                return@map ctor
            }
        } else for (method in methods) if (method.name == methodName && isMatchedExecutable(method, argArray)) {
            method.isAccessible = true
            return@map method
        }
        throw NoSuchMethodException(clazzName + "." + methodName + "(" + listOf(argArray) + ")")
    }.toTypedArray()
}

/**
 * 反射获取执行结果
 *
 * @param clazz   执行类
 * @param execStr 方法列表字符串
 * @param argsStr 参数列表字符串
 * @return 执行结果
 * @throws NoSuchMethodException     反射异常
 * @throws InvocationTargetException 反射异常
 * @throws InstantiationException    反射异常
 * @throws IllegalAccessException    反射异常
 */
fun invokeResults(clazz: KClass<*>, execStr: String, argsStr: String): List<Any?> {
    val methodNames: Array<String> = parseStringArray(execStr)
    val argsArray: Array<Array<Any?>> = parseObject(argsStr, Array<Array<Any?>>::class)
    require(methodNames.size == argsArray.size) { "methodNames.length != argsArray.length" }
    val executables = getExecutableArray(clazz.java, methodNames, argsArray)
    val n = executables.size
    val res = arrayOfNulls<Any>(n)
    var obj: Any? = null
    for (i in 0 until n) when (val exe = executables[i]) {
        is Constructor<*> -> obj = exe.newInstance(*argsArray[i])
        is Method -> res[i] = exe.invoke(obj, *argsArray[i])
        else -> println(exe)
    }
    return listOf(*res)
}

fun isMatchedExecutable(e: Executable, args: Array<Any?>): Boolean {
    val types = e.parameterTypes
    if (types.size != args.size) {
        return false
    }
    for (j in args.indices) {
        val type = types[j]
        val arg = args[j]
        val clazz = args[j]!!.javaClass
        if (!type.isAssignableFrom(clazz) && !clazz.isInstance(type)) {
            if (type.isPrimitive) {
                if (!type.isAssignableFrom(TypeUtilities.getPrimitiveType(clazz))) {
                    return false
                }
            } else when (arg) {
                is JSONArray -> arg.to(type).also { args[j] = it }
                is JSONObject -> args[j] = arg.to(type)
                else -> return false
            }
        }
    }
    return true
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@Log
internal class DTO {
    private var id: String? = null
    private var concurrentMap: AbstractMap<BigInteger, BigDecimal> = ConcurrentHashMap()
    private var map: Map<Instant, Clock> = TreeMap()
    private var list: List<Collections> = CopyOnWriteArrayList()
    private var collection: Queue<Calendar> = ConcurrentLinkedDeque()
    private var pathCompiler: JSONFactory.JSONPathCompiler = JSONFactory.getDefaultJSONPathCompiler()
    private var json: JSONObject = JSON.parseObject(JSONArray().toJSONString())
    private var field = Proxy.newProxyInstance(DTO::class.java.classLoader, DTO::class.java.interfaces)
    { o: Any?, m: Method, a: Array<Any?> -> m.invoke(o, *a) } as Field
    private var service: ExecutorService = Executors.newCachedThreadPool()
    private var pattern: Pattern = Pattern.compile("^.*?$")
    private var matcher: Matcher = pattern.matcher("")
    private var mode = RoundingMode.CEILING
    private var intStream: IntStream = StreamSupport.intStream(Spliterators.emptyIntSpliterator(), true)
    private var longStream: BaseStream<Long, LongStream> = LongStream.of().unordered()
    private var stream: Stream<InputStream> = Arrays.stream(arrayOfNulls<BufferedInputStream>(0))
    private var function = Function { obj: LocalDateTime -> obj.toLocalTime() }
    private var supplier = Supplier { LocalDate.now() }
    private var consumer = Consumer { _: Duration? -> }
    private var biFunction = BiFunction { _: FileReader?, _: FileWriter? -> File("") }
    private var intSupplier = Predicate { _: MathContext? -> true }
    private var path: Path = Paths.get(URI.create(AccessMode.READ.name))
    private var fileSystem: FileSystem = FileSystems.getDefault()

    companion object {
        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val treeNode = Objects.requireNonNull(parseTreeNode("[]"))
            val listNode = Objects.requireNonNull(parseListNode("[]"))
            println(
                DoubleStream.empty().boxed().collect(teeing(
                    groupingBy(
                        Function.identity(),
                        groupingByConcurrent(
                            { obj -> obj!!.toString() },
                            mapping({ d -> java.lang.Double.toHexString(d!!) }, toList())
                        )
                    ),
                    filtering(
                        { d: Double -> !d.isNaN() },
                        partitioningBy(
                            { d: Double? -> java.lang.Double.isFinite(d!!) },
                            flatMapping({ t: Double? -> Stream.of(t) }, toSet())
                        )
                    )
                ) { _, _ -> treeNode.toString() + listNode })
            )
            when (ThreadLocalRandom.current().nextInt(SocketOptions.SO_BROADCAST)) {
                1 -> throw SocketException()
                2 -> throw SocketTimeoutException()
                3 -> throw UnknownHostException()
                4 -> throw FileAlreadyExistsException("")
                else -> throw AccessDeniedException(StandardSocketOptions.SO_BROADCAST.name())
            }
        }
    }
}

@Anno
internal enum class DescribableEnum : Constable {
    A
}

@Inherited
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Repeatable(Anno.List::class)
internal annotation class Anno(val value: String = "") {
    @Inherited
    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    @Target
    annotation class List(vararg val value: Anno)
}
