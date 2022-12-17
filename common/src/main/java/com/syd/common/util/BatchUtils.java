package com.syd.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * @author songyide
 * @date 2022/7/11
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BatchUtils {
    /**
     * 默认批量插入条数
     */
    public static final int BATCH_SIZE_INSERT = 1 << 10;
    /**
     * 默认批量导出条数
     */
    public static final int BATCH_SIZE_EXPORT = 1 << 13;
    /**
     * 默认批量查询条数
     */
    public static final int BATCH_SIZE_QUERY = 1 << 15;

    /**
     * 分批查询
     *
     * @param func 使用分页参数查询数据
     * @param size 查询总数，如果为null，则查询全部
     * @return 迭代器
     */
    public static <T, R> Iterator<List<T>> newBatchQueryIterator(Function<IPage<R>, List<T>> func, Integer size) {
        return new Iterator<List<T>>() {
            final IPage<R> page = new Page<>(1, BATCH_SIZE_EXPORT);
            boolean hasNext = true;
            int cnt = 0;

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public List<T> next() {
                List<T> list = func.apply(page);
                if (list == null || list.size() == 0) {
                    hasNext = false;
                    return Collections.emptyList();
                }
                if (size != null && cnt + list.size() >= size) {
                    hasNext = false;
                    return list.subList(0, size - cnt);
                }
                cnt += list.size();
                page.setCurrent(page.getCurrent() + 1);
                return list;
            }
        };
    }

    /**
     * 批量插入，不要在func中增删list元素
     *
     * @return 插入的条数
     */
    public static <T, R> int batchInsert(List<T> list, Function<List<T>, R> func, int batchSize) {
        @SuppressWarnings("unchecked")
        R res = batchExec(list, func, batchSize, (o, n) -> {
            Object obj = o;
            if (n instanceof Number) {
                obj = ((Number)o).intValue() + ((Number)n).intValue();
            }
            return (R)obj;
        });
        if (res instanceof Number) {
            return ((Number)res).intValue();
        }
        return 0;
    }

    public static <T> int batchInsert(List<T> list, Function<List<T>, ?> func) {
        return batchInsert(list, func, BATCH_SIZE_INSERT);
    }

    /**
     * 批量转换
     */
    public static <T, R> List<R> batchTrans(List<T> idList, Function<List<T>, List<R>> func, int batchSize) {
        return batchExec(idList, func, batchSize, (o, n) -> {
            o.addAll(n);
            return o;
        });
    }

    public static <T, R> List<R> batchTrans(List<T> idList, Function<List<T>, List<R>> func) {
        return batchTrans(idList, func, BATCH_SIZE_INSERT);
    }

    /**
     * 批量查询后插入
     */
    public static <T, R> int batchInsertFromSelect(Function<IPage<T>, List<T>> supplier, Function<List<T>, R> consumer) {
        return batchInsertFromSelect(supplier, consumer, BATCH_SIZE_QUERY, BATCH_SIZE_INSERT);
    }

    /**
     * 批量查询后插入
     */
    public static <T, R> int batchInsertFromSelect(
            Function<IPage<T>, List<T>> supplier, Function<List<T>, R> consumer, int querySize, int insertSize) {
        IPage<T> page = new Page<>(1, querySize);
        long idx = 0;
        while (true) {
            List<T> list = supplier.apply(page);
            if (list == null || list.isEmpty()) {
                break;
            }
            idx += list.size();
            if (insertSize < querySize) {
                batchInsert(list, consumer, insertSize);
            } else {
                consumer.apply(list);
            }
            page.setCurrent(page.getCurrent() + 1);
        }
        return (int)idx;
    }

    private static <T, R> R batchExec(
            List<T> list, Function<List<T>, R> func, int batchSize,
            BinaryOperator<R> merger) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int size = list.size(), l = 0, r = batchSize;
        R res = null;
        for (; r < size; l = r, r += batchSize) {
            // 防止右边界溢出
            if (r < 0) {
                break;
            }
            R o = func.apply(list.subList(l, r));
            if (res == null) {
                res = o;
            } else {
                res = merger.apply(res, o);
            }
        }
        R o = func.apply(list.subList(l, size));
        if (res == null) {
            res = o;
        } else {
            res = merger.apply(res, o);
        }
        return res;
    }
}
