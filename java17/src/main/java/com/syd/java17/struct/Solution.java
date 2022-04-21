package com.syd.java17.struct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONObject;
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
public class Solution {
    public String toGoatLatin(String sentence) {
        int length = sentence.length();
        char[] value = new char[length + (1 + length) * length / 2];
        int index = 0;
        char c, f = 0, p = ' ';
        int count = 0;
        for (int i = 0; i < length; i++) {
            c = sentence.charAt(i);
            if (p == ' ') {
                f = c;
                switch (f) {
                    case 'A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u' -> value[index++] = f;
                    default -> {
                    }
                }
            } else if (c == ' ') {
                index = getIndex(value, index, f);
                ++count;
                for (int j = 0; j < count; j++) {
                    value[index++] = 'a';
                }
                value[index++] = ' ';
            } else {
                value[index++] = c;
            }
            p = c;
        }
        index = getIndex(value, index, f);
        for (int j = 0; j <= count; j++) {
            value[index++] = 'a';
        }
        return new String(value, 0, index);
    }

    private int getIndex(char[] value, int index, char f) {
        switch (f) {
            default:
                value[index++] = f;
            case 'A':
            case 'E':
            case 'I':
            case 'O':
            case 'U':
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                value[index++] = 'm';
                value[index++] = 'a';
        }
        return index;
    }

    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        int n;
        String s, s2;
        int[] nums, nums2;
        int[][] mat, mat2;
        String[] strs, strs2;
        List<Integer> li;
        List<String> ls;
        char[] cs;
        TreeNode root;
        ListNode node;

    }


    public static void printStringList(Collection<String> list) {
        System.out.print("[");
        Iterator<String> it = list.iterator();
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

    public static String listToString(List<?> list) {
        StringBuilder sb = new StringBuilder("[");
        for (Object o : list) {
            sb.append(o).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).append("]").toString();
    }

    public static String arrayToString(Object[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (Object o : array) {
            sb.append(o).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).append("]").toString();
    }

    public static Executable[] getExecutableArray(Class<?> clazz, String str, Map<String, Class<?>[]> argTypeMap)
            throws ClassNotFoundException, NoSuchMethodException {
        String[] strings = parseStringArray(str);
        int n = strings.length;
        Executable[] res = new Executable[n];
        res[0] = clazz.getDeclaredConstructor(argTypeMap.get(strings[0]));
        for (int i = 1; i < n; i++) {
            res[i] = clazz.getDeclaredMethod(strings[i], argTypeMap.get(strings[i]));
        }
        return res;
    }

    public static Object[][] getArgsArray(String str) {
        return parseObject(str, Object[][].class);
//        List<Object[]> parse = JSONArray.parseArray(str, Object[].class);
//        return parse.toArray(new Object[0][]);
    }

    public static Object[] getInvokeResults(
            Class<?> clazz, String execStr, String argsStr, Map<String, Class<?>[]> argTypeMap)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Executable[] executables = getExecutableArray(clazz, execStr, argTypeMap);
        Object[][] argsArray = getArgsArray(argsStr);
        assert executables.length == argsArray.length;
        int n = executables.length;
        Object[] results = new Object[n];
        assert executables[0] instanceof Constructor;
        Object obj = ((Constructor<?>) executables[0]).newInstance(argsArray[0]);
        for (int i = 1; i < n; i++) {
            assert executables[i] instanceof Method;
            results[i] = ((Method) executables[i]).invoke(obj, argsArray[i]);
        }
        return results;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    static class DTO {
        int a, b;
        AbstractMap<BigInteger, BigDecimal> concurrentMap = new ConcurrentHashMap<>();
        Map<Date, Arrays> map = new TreeMap<>();
        List<Collections> list = new CopyOnWriteArrayList<>();
        Queue<Calendar> collection = new ConcurrentLinkedDeque<>();
        JSONAware json = JSONObject.parseArray(new JSONArray().toJSONString());
        Field field = (Field) Proxy.newProxyInstance(DTO.class.getClassLoader(), DTO.class.getInterfaces(),
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


