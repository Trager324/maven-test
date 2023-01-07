package com.syd.common.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;


/**
 * Set工具类，更多方法使用{@link Sets}
 *
 * @author songyide
 * @see Sets
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetUtils {
    /**
     * 不区分大小写的Set, 允许null值
     *
     * @apiNote 后续提升性能考虑基于HashMap封装
     */
    public static Set<String> newCaseInsensitiveSet() {
        return Collections.newSetFromMap(MapUtils.newCaseInsensitiveMap());
    }

    /**
     * 不区分大小写的Set, 允许null值
     *
     * @see Sets#newHashSet(Iterable)
     */
    public static Set<String> newCaseInsensitiveSet(Iterable<String> elements) {
        var set = newCaseInsensitiveSet();
        if (elements instanceof Collection) {
            set.addAll((Collection<String>)elements);
        } else {
            Iterators.addAll(set, elements.iterator());
        }
        return set;
    }
}
