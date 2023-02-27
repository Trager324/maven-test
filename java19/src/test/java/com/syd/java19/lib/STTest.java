package com.syd.java19.lib;

import org.junit.jupiter.api.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class STTest {
    @Test
    void test0() {
        // 第二个和第三个参数用于定义表达式的头尾字符
        ST hello = new ST("Hello, $if(name)$$name$$endif$", '$', '$');
        hello.add("name", "world");
        assertEquals("Hello, world", hello.render());
    }

    @Test
    void testTemplateGroup() {
        STGroup stg = new STGroupString("""
                sqlTemplate(columns,condition) ::=
                "select <columns> from table where 1=1 <if(condition)>and <condition><endif>"
                """);
        ST sqlST = stg.getInstanceOf("sqlTemplate");
        sqlST.add("columns", "order_id");
        sqlST.add("condition", "dt='2017-04-04'");
        assertEquals("select order_id from table where 1=1 and dt='2017-04-04'", sqlST.render());
    }
}
enum A {

}
