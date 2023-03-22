package com.syd.java20.struct;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONFactory;
import com.alibaba.fastjson2.JSONObject;
import com.syd.algo.leetcode.ListNode;
import com.syd.algo.leetcode.TreeNode;
import jdk.dynalink.linker.support.TypeUtilities;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.ThreadSafe;
import java.io.*;
import java.lang.annotation.*;
import java.lang.constant.Constable;
import java.lang.reflect.Proxy;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.*;
import java.nio.file.FileSystem;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.*;

import static com.syd.algo.leetcode.ListNode.parseListNode;
import static com.syd.algo.leetcode.TreeNode.parseTreeNode;
import static java.util.stream.Collectors.*;

/**
 * @author asus
 */
@NoArgsConstructor
@Anno
@ThreadSafe
@Slf4j
public class Solution {
    static int f(int a) {
        return a + 1;
    }

    static String s = "" + BigDecimal.valueOf(1);
    public static void main(String[] args) throws Throwable {
        switch (args[0]) {
        }
    }

    static final Solution solution = new Solution();

    public static <T> T parseObject(String text, Class<T> clazz) {
        // inter procedural analysis
        return JSON.parseObject(text, clazz);
    }

    public static int[] parseIntArray(String str) {
        return parseObject(str, int[].class);
    }

    public static int[][] parseIntMatrix(String str) {
        return parseObject(str, int[][].class);
    }

    public static String[] parseStringArray(String str) {
        return parseObject(str, String[].class);
    }

    static boolean isMatchedExecutable(Executable e, Object[] args) {
        Class<?>[] types = e.getParameterTypes();
        if (types.length != args.length) {
            return false;
        }
        for (int j = 0; j < args.length; j++) {
            Class<?> type = types[j];
            Object arg = args[j];
            Class<?> clazz = args[j].getClass();
            if (!type.isAssignableFrom(clazz) && !clazz.isInstance(type)) {
                if (type.isPrimitive()) {
                    if (!type.isAssignableFrom(TypeUtilities.getPrimitiveType(clazz))) {
                        return false;
                    }
                } else {
                    switch (arg) {
                        case JSONArray json -> args[j] = json.to(type);
                        case JSONObject json -> args[j] = json.to(type);
                        default -> {return false;}
                    }
                }
            }
        }
        return true;
    }

    static Executable[] getExecutableArray(Class<?> clazz, String[] methodNames, Object[][] argsArray)
            throws NoSuchMethodException {
        int n = methodNames.length;
        Method[] methods = clazz.getDeclaredMethods();
        Constructor<?>[] ctors = clazz.getDeclaredConstructors();
        String clazzName = clazz.getSimpleName();
        Executable[] res = new Executable[n];
        for (int i = 0; i < n; i++) {
            String methodName = methodNames[i];
            Object[] argArray = argsArray[i];
            if (clazzName.equals(methodName)) {
                for (Constructor<?> ctor : ctors) {
                    if (isMatchedExecutable(ctor, argArray)) {
                        res[i] = ctor;
                        break;
                    }
                }
            } else {
                for (Method method : methods) {
                    if (method.getName().equals(methodName) && isMatchedExecutable(method, argArray)) {
                        res[i] = method;
                        break;
                    }
                }
            }
            if (res[i] == null) {
                throw new NoSuchMethodException(clazzName + "." + methodName + "(" + List.of(argArray) + ")");
            }
            res[i].setAccessible(true);
        }
        return res;
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
    public static List<Object> invokeResults(Class<?> clazz, String execStr, String argsStr)
            throws ReflectiveOperationException {
        var methodNames = parseStringArray(execStr);
        var argsArray = parseObject(argsStr, Object[][].class);
        if (methodNames.length != argsArray.length) {
            throw new IllegalArgumentException("methodNames.length != argsArray.length");
        }

        var executables = getExecutableArray(clazz, methodNames, argsArray);
        int n = executables.length;
        var res = new Object[n];
        Object obj = null;
        for (int i = 0; i < n; i++) {
            if (executables[i] instanceof Method method) {
                res[i] = method.invoke(obj, argsArray[i]);
            } else if (executables[i] instanceof Constructor<?> ctor) {
                obj = ctor.newInstance(argsArray[i]);
            }
        }
        return Arrays.asList(res);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@Log
class DTO {
    String id;
    AbstractMap<BigInteger, BigDecimal> concurrentMap = new ConcurrentHashMap<>();
    Map<Instant, Clock> map = new TreeMap<>();
    List<Collections> list = new CopyOnWriteArrayList<>();
    Queue<Calendar> collection = new ConcurrentLinkedDeque<>();
    JSONFactory.JSONPathCompiler pathCompiler = JSONFactory.getDefaultJSONPathCompiler();
    JSONObject json = JSON.parseObject(new JSONArray().toJSONString());
    Field field = (Field)Proxy.newProxyInstance(DTO.class.getClassLoader(), DTO.class.getInterfaces(),
            (o, m, a) -> m.invoke(o, a));
    ExecutorService service = Executors.newCachedThreadPool();
    Pattern pattern = Pattern.compile("^.*?$");
    Matcher matcher = pattern.matcher("");
    RoundingMode mode = RoundingMode.CEILING;
    IntStream intStream = StreamSupport.intStream(Spliterators.emptyIntSpliterator(), true);
    BaseStream<Long, LongStream> longStream = LongStream.of().unordered();
    Stream<InputStream> stream = Arrays.stream(new BufferedInputStream[0]);
    Function<LocalDateTime, LocalTime> function = LocalDateTime::toLocalTime;
    Supplier<LocalDate> supplier = LocalDate::now;
    Consumer<Duration> consumer = __ -> {};
    BiFunction<FileReader, FileWriter, File> biFunction = (r, w) -> new File("");
    Predicate<MathContext> intSupplier = __ -> true;
    Path path = (Paths.get(URI.create(AccessMode.READ.name())));
    FileSystem fileSystem = FileSystems.getDefault();

    public static void main(String[] args) throws IOException {
        TreeNode treeNode = Objects.requireNonNull(parseTreeNode("[]"));
        ListNode listNode = Objects.requireNonNull(parseListNode("[]"));
        System.out.println((String)DoubleStream.empty().boxed().collect(teeing(
                groupingBy(Function.identity(),
                        groupingByConcurrent(String::valueOf,
                                mapping(Double::toHexString, toList()))),
                filtering(d -> !d.isNaN(),
                        partitioningBy(Double::isFinite,
                                flatMapping(Stream::of, toSet()))),
                (u, v) -> treeNode.toString() + listNode)));
        switch (ThreadLocalRandom.current().nextInt(SocketOptions.SO_BROADCAST)) {
            case 1 -> throw new SocketException();
            case 2 -> throw new SocketTimeoutException();
            case 3 -> throw new UnknownHostException();
            case 4 -> throw new FileAlreadyExistsException("");
            default -> throw new AccessDeniedException(StandardSocketOptions.SO_BROADCAST.name());
        }
    }
}

enum DescribableEnum implements Constable {
    A;
}

@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Anno.List.class)
@interface Anno {
    String value() default "";

    @Inherited
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    @interface List {
        Anno[] value();
    }
}
