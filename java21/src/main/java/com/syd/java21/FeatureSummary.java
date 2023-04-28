package com.syd.java21;


import lombok.extern.slf4j.Slf4j;

import javax.lang.model.SourceVersion;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.constant.ClassDesc;
import java.lang.constant.DynamicConstantDesc;
import java.lang.constant.MethodHandleDesc;
import java.lang.constant.MethodTypeDesc;
import java.lang.foreign.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.spec.EdECPublicKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Consumer;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Summary of JDK Release Features
 *
 * @author songyide
 * @date 2022/6/17
 * @see SourceVersion
 */
@Slf4j
public class FeatureSummary {
    static final Random RANDOM = new Random();
    static final FeatureSummary summary = new FeatureSummary();

    public static void main(String[] args) throws Exception {
//        summary._20();
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
//            Stream.<Runnable>of(summary::_1_4, summary::_5, summary::_6, summary::_7, summary::_8, summary::_9,
//                            summary::_10, summary::_11, summary::_12, summary::_13, summary::_14,
//                            summary::_15, summary::_16, summary::_17, summary::_18, summary::_19,
//                            summary::_20);
//            Stream.<Runnable>of(summary::_17)
//                    .<Callable<Void>>map(r -> () -> {
//                        r.run();
//                        return null;
//                    })
//                    .forEach(scope::fork);
            scope.join();
            Executors.newCachedThreadPool().close();
        }
    }

    void _21() {

    }

    /**
     * @since 20
     */
    void _20() {
        try (Arena arena = Arena.ofShared()) {
            SequenceLayout SEQUENCE_LAYOUT = MemoryLayout.sequenceLayout(1024, ValueLayout.JAVA_INT);
            MemorySegment segment = arena.allocate(SEQUENCE_LAYOUT);
            long int_size = ValueLayout.JAVA_INT.byteSize();
            System.out.println(int_size);
            for (int i = 0; i < 1024; i++) {
                segment.setAtIndex(ValueLayout.JAVA_INT, i, 1);
            }
            int sum = segment.elements(ValueLayout.JAVA_INT).parallel()
                    .mapToInt(s -> s.get(ValueLayout.JAVA_INT, 0))
                    .sum();
            System.out.println(sum);
        }
    }

    /**
     * <ul>
     *     <li>switch(第三次预览), record解构, 解构分支when表达式</li>
     *     <li>虚拟线程</li>
     *     <li>结构化并发(孵化)</li>
     *     <li>外部函数和内存API(预览)</li>
     * </ul>
     *
     * @since 19
     */
    void _19() {
        I19 i = new I19[]{null, new Circle(new P(0, 0), 1),}[RANDOM.nextInt(2)];
        var x = switch (i) {
            case null -> 0;
            case A19 a -> a.hashCode();
            case B19 b -> b.hashCode();
            case Circle(P(var x0, var y0), var r) when r < x0 -> {
                System.out.println(r);
                yield y0;
            }
            case Circle(var p, var r) -> r + Math.abs(p.x) + Math.abs(p.y);
        };
        Thread.ofVirtual().start(() -> System.out.println(x));
    }

    /**
     * <ul>
     *     <li>
     *     javaDoc snippet标签
     *     {@snippet :
     *     class Main() {
     *         public static void main(String[] args) {
     *             System.out.println("since java18");
     *         }
     *     }}
     *    </li>
     * </ul>
     *
     * @since 18
     **/
    void _18() {}

    void _17() {
        Object o = new Object[]{"1", null, new P(0, 0)}[RANDOM.nextInt(3)];
        switch (o) {
            case null -> System.out.println("null");
            case String s -> System.out.println(s);
            default -> System.out.println();
        }
    }

    /**
     * <ul>
     *     <li>Vector API(孵化), Foreign Linker API(孵化)简化native code</li>
     *     <li>ZGC优化: 实现了并发thread-stack处理来降低GC safepoints的负担</li>
     *     <li>Elastic Metaspace, 及时地将未使用的 HotSpot 类元数据（即元空间）内存返回给操作系统,
     *     减少元空间占用, 并简化元空间代码以降低维护成本。</li>
     *     <li>instanceof模式匹配(正式); Record(正式); jpackage(正式)</li>
     * </ul>
     *
     * @since 16
     */
    void _16() {}

    /**
     * <ul>
     *   <li>Edwards-Curve 数字签名算法{@link EdECPublicKeySpec}</li>
     *   <li>Foreign-Memory Access API(二次孵化)</li>
     *   <li>文本块(正式){@link String#stripIndent}, {@link String#translateEscapes}, {@link String#formatted}</li>
     *   <li>{@link DatagramSocket}, {@link MulticastSocket}API重构</li>
     *   <li>移除Solaris 和 SPARC 端口; 移除the Nashorn JS引擎</li>
     *   <li>封闭类(预览), sealed, permits; 隐藏类; 禁用、弃用偏向锁</li>
     *   <li>Shenandoah GC(正式): 一种低停顿的垃圾回收器, -XX:+UseShenandoahGC 开启不需要添加参数
     *   -XX:+UnlockExperimentalVMOptions</li>
     *   <li>ZGC(正式)
     *     <ul>
     *      <li>ZGC是Java 11引入的新的垃圾收集器（JDK9以后默认的垃圾回收器是G1）, 经过了多个实验阶段, 自此终于成为正式特性。</li>
     *      <li>自 2018 年以来, ZGC 已增加了许多改进, 从并发类卸载、取消使用未使用的内存、对类数据共享的支持到改进的 NUMA 感知。
     *      此外, 最大堆大小从 4 TB 增加到 16 TB。支持的平台包括 Linux、Windows 和 MacOS。</li>
     *      <li>ZGC是一个重新设计的并发的垃圾回收器, 通过减少 GC 停顿时间来提高性能。</li>
     *      <li>默认的GC仍然还是G1；之前需要通过-XX:+UnlockExperimentalVMOptions -XX:+UseZGC来启用ZGC,
     *      现在只需要-XX:+UseZGC就可以。</li>
     *     </ul>
     *   </li>
     * </ul>
     *
     * @since 15
     */
    void _15() {
    }

    /**
     * <ul>
     *   <li>外部存储器访问 API(孵化); jpackage(孵化)</li>
     *   <li>switch表达式(正式); record(预览); instanceof模式匹配</li>
     *   <li>{@link NullPointerException}错误栈增强, 指出引发NPE的对象</li>
     *   <li>CMS移除, 弃用 ParallelScavenge + SerialOld GC; ZGC优化Windows和Mac</li>
     * </ul>
     *
     * @since 14
     */
    void _14() {
        Object o = String.valueOf(RANDOM.nextBoolean() ? String.valueOf(RANDOM.nextInt(5)) : 1);
        if (o instanceof String s) {
            System.out.println(switch (s) {
                case "1" -> 1;
                case "2" -> 2;
                default -> {
                    System.out.println("default");
                    yield 0;
                }
            });
        }
    }

    /**
     * <ul>
     *   <li>{@link Socket}, {@link ServerSocket}重构</li>
     *   <li>{@link FileSystems#newFileSystem}</li>
     *   <li>增强ZGC, 将未使用的堆内存返回给操作系统</li>
     *   <li>switch再增强, 引入yield</li>
     *   <li>多行文本块(预览)</li>
     * </ul>
     *
     * @since 13
     */
    void _13() {
        String text = """
                SELECT * FROM users
                WHERE id = #{userId}
                """;
        System.out.println(text);
    }

    /**
     * <ul>
     *   <li>JVM常量API{@link ClassDesc}, {@link MethodTypeDesc}, {@link MethodHandleDesc},
     *   {@link DynamicConstantDesc}</li>
     *   <li>{@link Collectors#teeing}流合并</li>
     *   <li>简化switch(预览); 核心库java.lang中支持Unicode11; 核心库java.text支持压缩数字格式; Shenandoah;
     *   安全库javax.net.ssl; G1可流动混合收集, 及时返回未使用的已分配内存</li>
     * </ul>
     *
     * @since 12
     */
    void _12() {
    }

    /**
     * <ul>
     *   <li>局部变量类型推断升级; ZGC; java命令直接运行源文件, 不需要事先javac编译</li>
     *   <li>{@link String#isBlank}, {@link String#strip}, {@link String#stripLeading}, {@link String#stripTrailing},
     *   {@link String#repeat}, {@link String#lines}, </li>
     *   <li>{@link Optional#isEmpty}</li>
     *   <li>HTTP客户端正式可用</li>
     * </ul>
     *
     * @since 11
     */
    void _11() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://foo.com/"))
                    .timeout(Duration.ofMinutes(2))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofFile(Paths.get("file.json")))
                    .build();

            try (HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .connectTimeout(Duration.ofSeconds(20))
                    .proxy(ProxySelector.of(new InetSocketAddress("proxy.example.com", 80)))
                    .authenticator(Authenticator.getDefault())
                    .build()) {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.statusCode());
                System.out.println(response.body());

                client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenAccept(System.out::println);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        Consumer<String> con = (var c) -> System.out.println(c.toLowerCase());
        con.accept("HELLO WORLD");
    }

    /**
     * <ul>
     *   <li>{@link Properties#storeToXML}</li>
     *   <li>{@link ByteArrayOutputStream#toString(Charset)}</li>
     *   <li>{@link Optional#orElseThrow}</li>
     *   <li>{@link List#copyOf}, {@link Set#copyOf}, {@link Map#copyOf}</li>
     *   <li>{@link Collectors#toUnmodifiableList}, {@link Collectors#toUnmodifiableSet},
     *   {@link Collectors#toUnmodifiableMap}</li>
     *   <li>静态类型推断var; GraalVM</li>
     * </ul>
     *
     * @since 10
     */
    void _10() {
        var a = "1";
        System.out.println(a.getClass());
    }

    /**
     * <ul>
     *   <li>模块化; jshell; 下划线作为关键字; String使用字节数组; 统一的JVM日志; HTTP客户端API引入; 接口私有方法;
     *   try-with-resource升级</li>
     *   <li>{@link InputStream#transferTo}</li>
     *   <li>Stream增强：{@link Stream#takeWhile}, {@link Stream#dropWhile}, {@link Stream#ofNullable},
     *   {@link Stream#iterate}方法重载, {@link Optional}转Stream
     *   <li>{@link List#of}, {@link Map#of}</li>
     *   </li>
     * </ul>
     *
     * @since 9
     */
    void _9() {
    }

    /**
     * <ul>
     *   <li>java.time新API: {@link LocalDateTime}, {@link Instant}, {@link Temporal}</li>
     *   <li>接口default方法; Nashorn JavaScript引擎</li>
     *   <li>lambda; 方法引用; {@link Stream}; {@link Optional}</li>
     * </ul>
     *
     * @since 8
     */
    void _8() {
    }

    /**
     * <ul>
     *   <li>常量表示: 0b1001, 1_000</li>
     *   <li>nio2: {@link Path}, {@link Paths}, {@link Files}</li>
     *   <li>try-with-resource; 泛型菱形语法自动推导类型; switch支持String; invokedynamic</li>
     * </ul>
     *
     * @since 7
     */
    void _7() {
    }

    /**
     * @since 6
     */
    void _6() {}

    /**
     * <ul>
     *   <li>泛型; 注解; 枚举; 可变参数; 自动拆装箱; 增强for循环; import static</li>
     * </ul>
     *
     * @since 5
     */
    void _5() {
    }

    /**
     * <ul>
     *   <li>1.1: JDBC; RMI; 反射; 内部类</li>
     *   <li>1.2: JIT; swing<li/>
     *   <li>1.4: XML; 日志; assert; 链式异常; IPV6; 正则表达式<li/>
     * </ul>
     *
     * @since 1-4
     */
    void _1_4() {
        class A {
            @SuppressWarnings("all")
            class NA {}
        }
        class B extends A {
            class NB extends A.NA {
                NB(A a) {
                    a.super();
                }
            }
        }
        B b = new B();
        System.out.println(b.new NB(b));
    }

    /**
     * <ul>
     *     <li>浮点运算更加严格, 简化{@link Math}, {@link StrictMath}</li>
     *     <li>删除AOT和JIT编译器; 删除Applet API; 弃用安全管理器Security Manager; 移除RMI激活机制</li>
     *     <li>为伪随机数生成器 (PRNG) 提供新的接口类型和实现, 包括可跳转 PRNG 和另一类可拆分 PRNG 算法 (LXM)。
     *     {@link RandomGenerator}为所有现有和新的PRNG提供统一的API。</li>
     *     <li>密封类(正式)</li>
     *     <li>switch模式匹配(预览)</li>
     * </ul>
     *
     * @since 17
     */
    sealed interface I19 permits A19, B19, Circle {}

    record P(int x, int y) {}

    record Circle(P c, int r) implements I19 {}

    static non-sealed private class A19 implements I19 {}

    static non-sealed private class B19 implements I19 {}
}
