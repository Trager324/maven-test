import com.alibaba.fastjson2.JSON;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class FastJsonBooleanTest {
    @Data
    static class A {
        private Boolean success = true;
        public Boolean isSuccess() {
            fail();
            return false;
        }
    }

    @Test
    void test() {
        System.out.println(JSON.toJSONString(new A()));
    }
}