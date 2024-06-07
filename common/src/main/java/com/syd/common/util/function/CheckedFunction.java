package com.syd.common.util.function;


import java.util.function.Function;

/**
 * 支持Exception的Function
 *
 * @param <T> the type of the first argument to the function
 * @param <R> the type of the result of the function
 * @see Function
 */
@FunctionalInterface
public interface CheckedFunction<T, R> extends Function<T, R> {
    @Override
    default R apply(T t) {
        try {
            return applyAndThrow(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @return the function result
     */
    R applyAndThrow(T t) throws Exception;
 
    /**
     * 以受检的方式接受CallSite，使其可以以Function形式使用
     */
    static <T, R> CheckedFunction<T, R> of(CheckedFunction<T, R> func) {
        return func;
    }
}