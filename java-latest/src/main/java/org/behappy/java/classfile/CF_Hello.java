package org.behappy.java.classfile;

import org.springframework.context.annotation.ComponentScan;

import java.lang.classfile.ClassFile;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.constant.ConstantDescs.*;

/**
 * @author syd
 */
@ComponentScan
@SuppressWarnings({"preview"})
public class CF_Hello {
    static MethodTypeDesc MTD_void_StringArray = MethodTypeDesc.of(CD_void, CD_String.arrayType());
    static ClassDesc CD_Hello = ClassDesc.of("org.behappy.test", "Hello");
    static ClassDesc CD_System = ClassDesc.of("java.lang.System");
    static ClassDesc CD_PrintStream = ClassDesc.of("java.io.PrintStream");
    static MethodTypeDesc MTD_void_String = MethodTypeDesc.of(CD_void, CD_String);

    public static void main(String[] args) throws Exception {
        byte[] bytes = ClassFile.of().build(CD_Hello,
                clb -> clb.withFlags(ClassFile.ACC_PUBLIC)
                        .withMethod(INIT_NAME, MTD_void,
                                ClassFile.ACC_PUBLIC,
                                mb -> mb.withCode(
                                        cob -> cob.aload(0)
                                                .invokespecial(CD_Object,
                                                        INIT_NAME, MTD_void)
                                                .return_()))
                        .withMethod("main", MTD_void_StringArray, ClassFile.ACC_PUBLIC + ClassFile.ACC_STATIC,
                                mb -> mb.withCode(
                                        cob -> cob.getstatic(CD_System, "out", CD_PrintStream)
                                                .ldc("Hello World.")
                                                .invokevirtual(CD_PrintStream, "println", MTD_void_String)
                                                .return_())));
        var className = CD_Hello.packageName() + "." + CD_Hello.displayName();
        var cl = new ByteClassLoader(new URL[0], ClassLoader.getSystemClassLoader()).addClass(className, bytes);
        Class<?> aClass = cl.findClass(className);
        Method main = aClass.getDeclaredMethod("main", String[].class);
        main.invoke(null, (Object) new String[]{"test"});
    }

    public static class ByteClassLoader extends URLClassLoader {
        private final Map<String, byte[]> extraClassDefs;

        public ByteClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
            this.extraClassDefs = new ConcurrentHashMap<>();
        }

        @Override
        protected Class<?> findClass(final String name) throws ClassNotFoundException {
            byte[] classBytes = this.extraClassDefs.remove(name);
            if (classBytes != null) {
                return defineClass(name, classBytes, 0, classBytes.length);
            }
            return super.findClass(name);
        }

        public ByteClassLoader addClass(String name, byte[] source) {
            extraClassDefs.put(name, source);
            return this;
        }
    }
}

