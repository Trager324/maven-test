package com.syd.java17.struct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONObject;
import com.syd.java17.util.algo.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.*;
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

    public List<String> findAndReplacePattern(String[] words, String pattern) {
        char[] set1 = new char[128], set2 = new char[128], cp = pattern.toCharArray();
        int n = cp.length;
        List<String> res = new ArrayList<>();
        for (String word : words) {
            char[] cw = word.toCharArray();
            int i = 0;
            for (; i < n; i++) {
                if (set1[cp[i]] == 0) {
                    if (set2[cw[i]] == 0) {
                        set1[cp[i]] = cw[i];
                        set2[cw[i]] = cp[i];
                    } else {
                        break;
                    }
                } else if (set1[cp[i]] != cw[i]) {
                    break;
                }
            }
            if (i == n) {
                res.add(word);
            }
            for (int j = 'a'; j <= 'z'; j++) {
                set1[j] = set2[j] = 0;
            }
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(SOLUTION.findAndReplacePattern(parseStringArray("[\"abc\",\"deq\",\"mee\",\"aqq\",\"dkd\",\"ccc\"]"), "abb"));
        System.out.println(SOLUTION.findAndReplacePattern(parseStringArray("[\"a\",\"b\",\"c\"]"), "a"));
    }

    static final Solution SOLUTION = new Solution();

    public static void printStringList(Collection<?> list) {
        System.out.print("[");
        Iterator<?> it = list.iterator();
        int n = list.size() - 1;
        for (int i = 0; i < n; i++) {
            System.out.printf("\"%s\", ", it.next());
        }
        System.out.printf("\"%s\"]", it.next());
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
        StringBuilder sb = new StringBuilder("[");
        for (Object o : array) {
            String tmp = o instanceof String ? "\"" + o + "\"" : o.toString();
            sb.append(tmp).append(",");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.append("]").toString();
    }

    public static Executable[] getExecutableArray(Class<?> clazz, String[] methodNames, String[][] argLists)
            throws NoSuchMethodException {
        int n = methodNames.length;
        Method[] methods = clazz.getDeclaredMethods();
        Constructor<?>[] ctors = clazz.getDeclaredConstructors();
        String clazzName = clazz.getSimpleName();
        Executable[] res = new Executable[n];
        for (int i = 0; i < n; i++) {
            String methodName = methodNames[i];
            String[] argList = argLists[i];
            if (clazzName.equals(methodName)) {
                for (Constructor<?> ctor : ctors) {
                    if (ctor.getParameterCount() == argList.length) {
                        res[i] = ctor;
                        break;
                    }
                }
                if (res[i] == null) {
                    throw new NoSuchMethodException(clazzName + "(" + array2Str(argList) + ")");
                }
            } else {
                for (Method method : methods) {
                    if (method.getName().equals(methodName) && method.getParameterCount() == argList.length) {
                        res[i] = method;
                        break;
                    }
                }
                if (res[i] == null) {
                    throw new NoSuchMethodException(clazzName + "." + methodName + "(" + array2Str(argList) + ")");
                }
            }
            res[i].setAccessible(true);
        }
        return res;
    }

    public static Object[] invokeResults(Class<?> clazz, String execStr, String argsStr)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String[] methodNames = parseStringArray(execStr);
        String[][] argLists = parseObject(argsStr, String[][].class);
        if (methodNames.length != argLists.length) {
            throw new IllegalArgumentException("methodNames.length != argLists.length");
        }

        Executable[] executables = getExecutableArray(clazz, methodNames, argLists);
        Object[][] argsArray = parseObject(argsStr, Object[][].class);
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
    static class DTO {
        String id;
        AbstractMap<BigInteger, BigDecimal> concurrentMap = new ConcurrentHashMap<>();
        Map<Date, Arrays> map = new TreeMap<>();
        List<Collections> list = new CopyOnWriteArrayList<>();
        Queue<Calendar> collection = new ConcurrentLinkedDeque<>();
        JSONAware json = JSONObject.parseArray(new JSONArray().toJSONString());
        Field field = (Field)Proxy.newProxyInstance(DTO.class.getClassLoader(), DTO.class.getInterfaces(),
                (o, m, a) -> m.invoke(o, a));
        ExecutorService service = Executors.newCachedThreadPool();
        Pattern pattern = Pattern.compile("^.*?$");
        Matcher matcher = pattern.matcher("");
        RoundingMode mode = RoundingMode.CEILING;
        IntStream intStream = StreamSupport.intStream(Spliterators.emptyIntSpliterator(), true);
        DoubleStream doubleStream = StreamSupport.doubleStream(Spliterators.emptyDoubleSpliterator(), true);
        Stream<Integer> stream = Arrays.stream(new int[0]).boxed().collect(Collectors.toList()).stream();


        public static void main(String[] args) {
            parseTreeNode("[]");
            parseListNode("[]");
        }
    }
}


