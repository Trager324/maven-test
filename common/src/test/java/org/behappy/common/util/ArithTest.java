package org.behappy.common.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author songyide
 * @date 2022/5/18
 */
public class ArithTest {

    @Test
    public void testExchange() {
        assertEquals(BigDecimal.TEN, Arith.exchange(BigDecimal.ONE, BigDecimal.TEN));
        assertEquals(BigDecimal.ONE, Arith.exchange(BigDecimal.ONE, null));
    }
}
