package com.syd.java20;

import java.lang.invoke.*;

import static java.lang.invoke.MethodHandles.lookup;
import static java.lang.invoke.MethodType.methodType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestA {

    private static final MethodHandle printArgs;

    static {
        MethodHandles.Lookup lookup = lookup();
        Class thisClass = lookup.lookupClass();  // (who am I?)
        try {
            printArgs = lookup.findStatic(thisClass,
                    "printArgs", methodType(void.class, Object[].class));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //    static void test() throws Throwable {
    //        // THE FOLLOWING LINE IS PSEUDOCODE FOR A JVM INSTRUCTION
    //        InvokeDynamic[#bootstrapDynamic].baz("baz arg", 2, 3.14);
    //    }
    private static void printArgs(Object... args) {
        System.out.println(java.util.Arrays.deepToString(args));
    }

    private static CallSite bootstrapDynamic(MethodHandles.Lookup caller, String name, MethodType type) {
        // ignore caller and name, but match the type:
        return new ConstantCallSite(printArgs.asType(type));
    }

    public static void main(String[] args) throws Throwable {
        MutableCallSite name = new MutableCallSite(methodType(String.class));
        MethodHandle MH_name = name.dynamicInvoker();
        MethodType MT_str1 = methodType(String.class);
        MethodHandle MH_upcase = lookup().findVirtual(String.class, "toUpperCase", MT_str1);
        MethodHandle worker1 = MethodHandles.filterReturnValue(MH_name, MH_upcase);
        name.setTarget(MethodHandles.constant(String.class, "Rocky"));
        assertEquals("ROCKY", (String) worker1.invokeExact());
        name.setTarget(MethodHandles.constant(String.class, "Fred"));
        assertEquals("FRED", (String) worker1.invokeExact());  // (mutation can be continued indefinitely)
        // The same call site may be used in several places at once.
        MethodType MT_str2 = methodType(String.class, String.class);
        MethodHandle MH_cat = lookup().findVirtual(String.class, "concat", methodType(String.class, String.class));
        MethodHandle MH_dear = MethodHandles.insertArguments(MH_cat, 1, ", dear?");
        MethodHandle worker2 = MethodHandles.filterReturnValue(MH_name, MH_dear);
        assertEquals("Fred, dear?", (String) worker2.invokeExact());
        name.setTarget(MethodHandles.constant(String.class, "Wilma"));
        assertEquals("WILMA", (String) worker1.invokeExact());
        assertEquals("Wilma, dear?", (String) worker2.invokeExact());

    }
}
