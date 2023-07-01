package com.syd.java20.test;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestJackson {
    @JsonFilter("filter")
    public static class A {
        public final String fa;

        public A(final String fa) {this.fa = fa;}
    }

    @JsonFilter("filter")
    public static class B {
        public final String fb;
        public final List<A> lb;

        public B(final String fb, final List<A> lb) {
            this.fb = fb;
            this.lb = lb;
        }
    }

    @JsonFilter("filter")
    public static class Foo {
        public final String ff;
        public final List<B> lf;

        public Foo(final String ff, final List<B> lf) {
            this.ff = ff;
            this.lf = lf;
        }
    }

    public static class MyFilter extends SimpleBeanPropertyFilter {
        private final Map<Class<?>, Set<String>> includePropMap;

        public MyFilter(final Map<Class<?>, Set<String>> includePropMap) {
            this.includePropMap = includePropMap;
        }

        @Override
        protected boolean include(final BeanPropertyWriter writer) {
            return false;
        }

        @Override
        protected boolean include(final PropertyWriter writer) {
            if (writer instanceof BeanPropertyWriter) {
                final Class<?> cls = writer.getMember().getDeclaringClass();
                final Set<String> includePropSet = includePropMap.get(cls);
                return includePropSet == null || includePropSet.isEmpty() ||
                        includePropSet.contains(writer.getName());
            }
            return true;
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        final B b = new B("B1", List.of(new A("A1"), new A("A2")));
        final Foo foo = new Foo("foo", List.of(b));
        final ObjectMapper mapper = new ObjectMapper();
        final SimpleFilterProvider filters = new SimpleFilterProvider();
        final Map<Class<?>, Set<String>> excludePropMap = Map.of(B.class, Set.of("lb"));
        filters.addFilter("filter", new MyFilter(excludePropMap));
        mapper.setFilterProvider(filters);
        final ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
        System.out.println(objectWriter.writeValueAsString(foo));
    }
}
