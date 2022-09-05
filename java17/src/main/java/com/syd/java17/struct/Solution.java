package com.syd.java17.struct;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONFactory;
import com.alibaba.fastjson2.JSONObject;
import jdk.dynalink.linker.support.TypeUtilities;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

import java.io.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.*;

import static com.syd.java17.struct.ListNode.parseListNode;
import static com.syd.java17.struct.TreeNode.parseTreeNode;

/**
 * @author asus
 */
@NoArgsConstructor
public class Solution {
    static class Node {
        String s;
        int off;
        Node(String s, int off) {
            this.s = s;
            this.off = off;
        }
    }
    public int kSimilarity(String s1, String s2) {
        Set<String> set = new HashSet<>();
        char[] cs2 = s2.toCharArray();

        Queue<Node> q = new LinkedList<>();
        set.add(s1);
        q.offer(new Node(s1, 0));
        int cnt = 0, n = cs2.length;
        for (int i = 0; i < n; i++) {
            int size = q.size();
            for (int j = 0; j < size; j++) {
                Node now = q.poll();
                String s = now.s;
                int k = now.off;
                for (; k < n; k++) {
                    if (s.charAt(k) != cs2[k]) {
                        break;
                    }
                }
                if (k == n) {
                    return cnt;
                }
                for (int l = k + 1; l < n; l++) {
                    if (s.charAt(l) == cs2[k]) {
                        String next = s.substring(0, k) + cs2[k] + s.substring(k + 1, l) + s.charAt(k) +
                                s.substring(l + 1);
                        if (!set.contains(next)) {
                            set.add(next);
                            q.offer(new Node(next, k + 1));
                        }
                    }
                }
            }
            cnt++;
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
//        System.out.println("1".substring(1));
        System.out.println(solution.kSimilarity("bccaba", "abacbc"));
    }

    static final Solution solution = new Solution();

    static void test(List<Map<Integer, ?>> list) {
        System.out.println(list);
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
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

    public static String list2Str(Collection<?> c) {
        StringBuilder sb = new StringBuilder("[");
        for (Object o : c) {
            String tmp = o instanceof String ? "\"" + o + "\"" : o.toString();
            sb.append(tmp).append(",");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.append("]").toString();
    }

    public static String array2Str(Object[] array) {
        return list2Str(Arrays.asList(array));
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
                throw new NoSuchMethodException(clazzName + "." + methodName + "(" + array2Str(argArray) + ")");
            }
            res[i].setAccessible(true);
        }
        return res;
    }

    public static Object[] invokeResults(Class<?> clazz, String execStr, String argsStr)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String[] methodNames = parseStringArray(execStr);
        Object[][] argsArray = parseObject(argsStr, Object[][].class);
        if (methodNames.length != argsArray.length) {
            throw new IllegalArgumentException("methodNames.length != argsArray.length");
        }

        Executable[] executables = getExecutableArray(clazz, methodNames, argsArray);
        int n = executables.length;
        Object[] res = new Object[n];
        Object obj = null;
        for (int i = 0; i < n; i++) {
            if (executables[i] instanceof Constructor<?> ctor) {
                obj = ctor.newInstance(argsArray[i]);
            } else if (executables[i] instanceof Method method) {
                res[i] = method.invoke(obj, argsArray[i]);
            }
        }
        return res;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    @Accessors(chain = true)
    @Log
    static class DTO {
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

        public static void main(String[] args) {
            parseTreeNode("[]");
            parseListNode("[]");
        }
    }
}


