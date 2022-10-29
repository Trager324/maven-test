package com.syd.java19.struct;

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

import static com.syd.java19.struct.ListNode.parseListNode;
import static com.syd.java19.struct.TreeNode.parseTreeNode;

/**
 * @author asus
 */
@NoArgsConstructor
@Data
public class Solution {
    Map<Integer, Integer> idxMap = new HashMap<>();
    void dfs(List<Integer> list, TreeNode t, int h) {
        if (t.left == null && t.right == null) {
            idxMap.put(t.val, list.size());
            list.add(h);
            return;
        }
        if (t.left != null) dfs(list, t.left, h + 1);
        idxMap.put(t.val, list.size());
        list.add(h);
        if (t.right != null) dfs(list, t.right, h + 1);
    }
    int[] dfs(Map<Integer, int[]> map, TreeNode t) {
        if (t.left == null && t.right == null) {
            var idx = idxMap.get(t.val);
            var res = new int[]{idx, idx};
            map.put(t.val, res);
            return res;
        }
        int ll = idxMap.get(t.val), rr = ll;
        if (t.left != null) {
            var a = dfs(map, t.left);
            ll = Math.min(ll, a[0]);
            rr = Math.max(rr, a[1]);
        }
        if (t.right != null) {
            var a = dfs(map, t.right);
            ll = Math.min(ll, a[0]);
            rr = Math.max(rr, a[1]);
        }
        int[] res = new int[]{ll, rr};
        map.put(t.val, res);
        return res;
    }
    public static class SegTree {
        // Let UNIQUE be a value which does NOT
        // and will not appear in the segment tree
        private static final long UNIQUE = 0;
        private final int size;
        // Segment tree values
        private final long[] tree;

        public SegTree(int size) {
            tree = new long[2 * (this.size = size)];
            java.util.Arrays.fill(tree, UNIQUE);
        }

        public SegTree(long[] values) {
            this(values.length);
            for (int i = 0; i < size; i++) modify(i, values[i]);
        }

        // This is the segment tree function we are using for queries.
        // The function must be an associative function, meaning
        // the following property must hold: f(f(a,b),c) = f(a,f(b,c)).
        // Common associative functions used with segment trees
        // include: min, max, sum, product, GCD, and etc...
        private long function(long a, long b) {
//        return a + b; // sum over a range
            return Math.max(a, b); // maximum value over a range
//        return Math.min(a, b); // minimum value over a range
//        return a * b; // product over a range (watch out for overflow!)
        }

        // Adjust point i by a value, O(log(n))
        public void modify(int i, long value) {
            //tree[i + N] = function(tree[i + N], value);
            tree[i + size] = value;
            for (i += size; i > 1; i >>= 1) {
                tree[i >> 1] = function(tree[i], tree[i ^ 1]);
            }
        }

        // Query interval [l, r), O(log(n)) ----> notice the exclusion of r
        public long query(int l, int r) {
            long res = UNIQUE;
            for (l += size, r += size; l < r; l >>= 1, r >>= 1) {
                if ((l & 1) != 0) res = function(res, tree[l++]);
                if ((r & 1) != 0) res = function(res, tree[--r]);
            }
            return res;
        }
    }
    public int[] treeQueries(TreeNode root, int[] queries) {
        List<Integer> list = new ArrayList<>();
        dfs(list, root, 0);
        Map<Integer, int[]> map = new HashMap<>();
        dfs(map, root);
        long[] arr = new long[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        SegTree st = new SegTree(arr);
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] range = map.get(queries[i]);
            res[i] = (int) Math.max(st.query(0, range[0]), st.query(range[1] + 1, arr.length));
        }
        return res;
    }
    static final Solution solution = new Solution();

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution.treeQueries(
                parseTreeNode("[1,null,5,3,null,2,4]"),
                parseIntArray("[3,5,4,2,4]")
        )));
        System.out.println(Arrays.toString(solution.treeQueries(
                parseTreeNode("[1,3,4,2,null,6,5,null,null,null,null,null,7]"),
                parseIntArray("[4]")
        )));
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

    void dfs(List<String> list, int[] idxes, int idx, char[] cs) {
        if (idx == idxes.length) {
            list.add(new String(cs));
            return;
        }
        cs[idxes[idx]] = Character.toLowerCase(cs[idxes[idx]]);
        dfs(list, idxes, idx + 1, cs);
        cs[idxes[idx]] = Character.toUpperCase(cs[idxes[idx]]);
        dfs(list, idxes, idx + 1, cs);
    }

    public List<String> letterCasePermutation(String s) {
        List<String> res = new ArrayList<>();
        int[] idxes = IntStream.range(0, s.length())
                .filter(i -> Character.isLetter(s.charAt(i)))
                .toArray();
        dfs(res, idxes, 0, s.toCharArray());
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


