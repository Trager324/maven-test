package com.behappy.java;


//import org.springframework.lang.NonNull;
//import org.springframework.lang.Nullable;

import io.netty.util.internal.shaded.org.jctools.queues.MpmcArrayQueue;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.classfile.ClassFile;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.MethodTypeDesc;
import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

import static java.lang.constant.ConstantDescs.CD_String;
import static java.lang.constant.ConstantDescs.CD_void;


@SuppressWarnings("preview")
@Data
public class Main {
    static MethodTypeDesc MTD_void_StringArray = MethodTypeDesc.of(CD_void, CD_String.arrayType());
    static ClassDesc CD_Hello = ClassDesc.of("org.behappy.test", "Hello");
    static ClassDesc CD_System = ClassDesc.of("java.lang.System");
    static ClassDesc CD_PrintStream = ClassDesc.of("java.io.PrintStream");
    static MethodTypeDesc MTD_void_String = MethodTypeDesc.of(CD_void, CD_String);
    List<Integer> list;

    @Nullable
    static String funNullable(@Nullable String def) {
        return def;
    }

    @Nonnull
    static String funNonNull(@Nonnull String def) {
        return funNullable(def);
    }

    public static void main(String[] args) {
        byte[] bytes = ClassFile.of().build(CD_Hello,
                clb -> clb.withFlags(ClassFile.ACC_PUBLIC)
                        .withMethod(ConstantDescs.INIT_NAME, ConstantDescs.MTD_void,
                                ClassFile.ACC_PUBLIC,
                                mb -> mb.withCode(
                                        cob -> cob.aload(0)
                                                .invokespecial(ConstantDescs.CD_Object,
                                                        ConstantDescs.INIT_NAME, ConstantDescs.MTD_void)
                                                .return_()))
                        .withMethod("main", MTD_void_StringArray, ClassFile.ACC_PUBLIC + ClassFile.ACC_STATIC,
                                mb -> mb.withCode(
                                        cob -> cob.getstatic(CD_System, "out", CD_PrintStream)
                                                .ldc("Hello World")
                                                .invokevirtual(CD_PrintStream, "println", MTD_void_String)
                                                .return_())));

        Instrumentation inst;

    }
}
