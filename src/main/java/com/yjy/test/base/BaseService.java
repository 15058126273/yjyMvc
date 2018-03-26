package com.yjy.test.base;

import com.yjy.test.util.hibernate.Condition;
import com.yjy.test.util.hibernate.OrderBy;
import com.yjy.test.util.hibernate.Pagination;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends BaseEntity> {

    T save(T entity);

    T update(T entity);

    T delete(T entity);

    T get(Serializable id);

    T load(Serializable id);

    T saveOrUpdate(T entity);

    T merge(T entity);

    T delete(Serializable id);

    void refresh(T entity);

    /**
     * 获取所有数据
     * @param orders 分页规则
     * @return 所有数据
     */
    List<T> getAll(OrderBy... orders);

    /**
     * 分页查询
     * @param pageNo 页号
     * @param pageSize 条数
     * @param orders 排序规则
     * @return 分页数据
     */
    Pagination getPage(int pageNo, int pageSize, OrderBy... orders);

    /**
     * 根据实体对象获取分页数据
     * @param t 实体
     * @param pageNo 页号
     * @param pageSize 条数
     * @param orders 排序规则
     * @return 分页数据
     */
    Pagination getPageByT(T t, int pageNo, int pageSize, OrderBy... orders);

    /**
     * 根据实体对象获取分页数据
     * @param t 实体
     * @param pageNo 页号
     * @param pageSize 条数
     * @param anywhere 是否模糊匹配
     * @param conditions 其他条件
     * @param exclude 需要排除的属性条件
     * @return 分页数据
     */
    Pagination getPageByT(T t, int pageNo, int pageSize, boolean anywhere, Condition[] conditions, String... exclude);

    /**
     * 获取总记录数
     * @return 总记录数
     */
    int getCount();

    /**
     * 获取记录数
     * @param t 实体对象
     * @return 记录数
     */
    int getCountByT(T t);

    /**
     * 获取记录数
     * @param t 实体对象
     * @param anyWhere 是否模糊匹配
     * @return 记录数
     */
    int getCountByT(T t, boolean anyWhere);

    /**
     * 根据一个属性获取记录数
     * @param property 属性
     * @param value 值
     * @return 记录数
     */
    int getCountByProperty(String property, Object value);

    /**
     * 按属性查找对象列表
     * @param property 属性
     * @param value 值
     * @return 结果列表
     */
    List<T> getByProperty(String property, Object value);

    /**
     * 按属性查找唯一对象
     * @param property 属性
     * @param value 值
     * @return 结果
     */
    T getUniqueByProperty(String property, Object value);

}
