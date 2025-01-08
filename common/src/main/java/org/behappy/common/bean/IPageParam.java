package org.behappy.common.bean;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author songyide
 * @date 2022/9/5
 */
public interface IPageParam {
    int DEF_CURRENT = 1;
    int DEF_SIZE = 10;
    IPageParam DEFAULT_PAGE_PARAM = new PageParam() {
        @Override
        public void setCurrent(Integer current) {
            throw new UnsupportedOperationException("公有参数不支持修改");
        }

        @Override
        public void setSize(Integer size) {
            throw new UnsupportedOperationException("公有参数不支持修改");
        }

        @Override
        public void setOrders(List<Sort.Order> orders) {
            throw new UnsupportedOperationException("公有参数不支持修改");
        }
    };

    /**
     * 页码
     *
     * @return 页码
     */
    Integer getCurrent();

    /**
     * 设置页码
     *
     * @param current 页码
     */
    void setCurrent(Integer current);

    /**
     * 页大小
     *
     * @return 页大小
     */
    Integer getSize();

    /**
     * 页大小
     *
     * @param size 页大小
     */
    void setSize(Integer size);

    /**
     * 排序字段
     *
     * @return 排序字段
     */
    List<Sort.Order> getOrders();

    /**
     * 排序字段
     *
     * @param orders 排序字段
     */
    void setOrders(List<Sort.Order> orders);

    /**
     * 快捷设置分页参数
     *
     * @param page 分页参数
     */
    default void setPageParam(Pageable page) {
        setCurrent(page.getPageNumber());
        setSize(page.getPageSize());
    }

    /**
     * 获取排序字段过滤器，避免sql注入
     * <p><b>默认拒绝所有排序字段</b></p>
     *
     * @return 排序字段过滤器
     */
    default Predicate<Sort.Order> getOrderItemFilter() {
        return o -> false;
    }

    /**
     * 转化成mybatis plus中的Page
     * <p><b>mbp的分页排序没有安全检查措施，需要避免SQL注入</b></p>
     *
     * @return 分页参数
     * @see Pageable
     */
    @NonNull
    default <T> Pageable newPage() {
        PageRequest page = getCurrent() == null || getSize() == null
                ? PageRequest.of(DEF_CURRENT, Integer.MAX_VALUE)
                : PageRequest.of(getCurrent(), getSize());
        var orders = getOrders();
        if (orders != null) {
            page = PageRequest.of(page.getPageNumber(), page.getPageSize(),
                    Sort.by(orders.stream()
                            .filter(getOrderItemFilter())
                            .toList()));
        }
        return page;
    }

    /**
     * 分页结果
     *
     * @param list 列表
     * @return 分页结果
     */
    default <T> Page<T> paging(List<T> list) {
        return new PageImpl<>(list);
    }
}
