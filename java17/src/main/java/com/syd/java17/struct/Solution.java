package com.syd.java17.struct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    public int[] createTargetArray(int[] nums, int[] index) {
        int n = index.length;
        List<Integer> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(index[i], nums[i]);
        }
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = list.get(i);
        }
        return res;
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

//        System.out.println(solution.findTheDistanceValue(parseIntArray("[4,5,8]"), parseIntArray("[10,9,1,8]"), 2));
//        System.out.println(solution.findTheDistanceValue(parseIntArray("[-3,2,-5,7,1]"), parseIntArray("[4]"), 84));
//        System.out.println(solution.findTheDistanceValue(parseIntArray("[2,1,100,3]"), parseIntArray("[-5,-2,10,-3,7]"), 6));
//        System.out.println(solution.findTheDistanceValue(parseIntArray("[-803,715,-224,909,121,-296,872,807,715,407,94,-8,572,90,-520,-867,485,-918,-827,-728,-653,-659,865,102,-564,-452,554,-320,229,36,722,-478,-247,-307,-304,-767,-404,-519,776,933,236,596,954,464]"), parseIntArray("[817,1,-723,187,128,577,-787,-344,-920,-168,-851,-222,773,614,-699,696,-744,-302,-766,259,203,601,896,-226,-844,168,126,-542,159,-833,950,-454,-253,824,-395,155,94,894,-766,-63,836,-433,-780,611,-907,695,-395,-975,256,373,-971,-813,-154,-765,691,812,617,-919,-616,-510,608,201,-138,-669,-764,-77,-658,394,-506,-675,523,730,-790,-109,865,975,-226,651,987,111,862,675,-398,126,-482,457,-24,-356,-795,-575,335,-350,-919,-945,-979,611]"), 3));
//        System.out.println(0b11);
//        System.out.println((0b1100 & 0b111) == 0b1111);
        System.out.printf("%+7.4f %+7.4f", Math.PI, Math.E);
    }


    public static void printStringList(Collection<String> list) {
        System.out.print("[");
        Iterator<String> it = list.iterator();
        int n = list.size() - 1;
        for (int i = 0; i < n; i++) {
            System.out.printf("\"%s\", ", it.next());
        }
        ExecutorService executor = Executors.newCachedThreadPool();
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


