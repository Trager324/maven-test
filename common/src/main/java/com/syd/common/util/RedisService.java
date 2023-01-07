package com.syd.common.util;

import com.syd.common.constant.StringPool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author songyide
 **/
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value, long time) {
        set(key, value, time, TimeUnit.SECONDS);
    }

    public void set(String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    public long atomicInc(String key, long time, TimeUnit timeUnit) {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        redisAtomicLong.expire(time, timeUnit);
        return redisAtomicLong.incrementAndGet();
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T)redisTemplate.opsForValue().get(key);
    }

    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    public Long del(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    public Boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Long inc(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public Double inc(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @SuppressWarnings("unchecked")
    public <T, HK> T hGet(String key, HK hashKey) {
        return (T)redisTemplate.opsForHash().get(key, hashKey);
    }

    public <HK> void hPut(String key, HK hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public <HK> Boolean hPut(String key, HK hashKey, Object value, long time) {
        hPut(key, hashKey, value);
        return expire(key, time);
    }

    public <HK, HV> Map<HK, HV> hEntries(String key) {
        return redisTemplate.<HK, HV>opsForHash().entries(key);
    }

    public void hPutAll(String key, Map<?, ?> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public Boolean hPutAll(String key, Map<?, ?> map, long time) {
        hPutAll(key, map);
        return expire(key, time);
    }

    public void hDel(String key, Object... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    public <HK> Boolean hHasKey(String key, HK hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public <HK> Long hInc(String key, HK hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    public <HK> Double hInc(String key, HK hashKey, double delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public <E> Set<E> sMembers(String key) {
        var members = redisTemplate.opsForSet().members(key);
        return members == null ? Set.of() : Set.of((E[])members.toArray());
    }

    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    public Long sAdd(String key, long time, Object... values) {
        Long count = sAdd(key, values);
        expire(key, time);
        return count;
    }

    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public <E> List<E> lRange(String key, long start, long end) {
        var list = redisTemplate.opsForList().range(key, start, end);
        return list == null ? List.of() : List.of((E[])list.toArray());
    }

    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T lIndex(String key, long index) {
        return (T)redisTemplate.opsForList().index(key, index);
    }

    public Long lPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public Long lPush(String key, Object value, long time) {
        Long index = lPush(key, value);
        expire(key, time);
        return index;
    }

    public Long lPushAll(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    public Long lPushAll(String key, long time, Object... values) {
        Long count = lPushAll(key, values);
        expire(key, time);
        return count;
    }

    public Long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    public Set<String> getListKey(String prefix) {
        return redisTemplate.keys(prefix + StringPool.STAR);
    }
}
