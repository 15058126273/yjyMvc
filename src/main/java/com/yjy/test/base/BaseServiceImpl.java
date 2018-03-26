package com.yjy.test.base;

import com.yjy.test.util.hibernate.Condition;
import com.yjy.test.util.hibernate.OrderBy;
import com.yjy.test.util.hibernate.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class BaseServiceImpl<T extends BaseEntity> extends BaseClass implements BaseService<T> {

    private static final Logger log = LogManager.getLogger(BaseServiceImpl.class);

    private BaseDao<T> dao;

    public void setDao(BaseDao<T> dao) {
        this.dao = dao;
    }

    protected BaseDao<T> getDao() {
        return this.dao;
    }

    public T save(T entity) {
        return dao.save(entity);
    }

    public T update(T entity) {
        return dao.update(entity);
    }

    public T delete(T entity) {
        return dao.delete(entity);
    }

    public T get(Serializable id) {
        return dao.get(id);
    }

    public T load(Serializable id) {
        return dao.load(id);
    }

    public T saveOrUpdate(T entity) {
        return dao.saveOrUpdate(entity);
    }

    public T merge(T entity) {
        return dao.merge(entity);
    }

    public T delete(Serializable id) {
        return dao.delete(id);
    }

    public void refresh(T entity) {
        dao.refresh(entity);
    }

    /**
     * 获取所有数据
     * @param orders 分页规则
     * @return 所有数据
     */
    public List<T> getAll(OrderBy... orders) {
        return dao.getAll(orders);
    }

    /**
     * 分页查询
     * @param pageNo 页号
     * @param pageSize 条数
     * @param orders 排序规则
     * @return 分页数据
     */
    public Pagination getPage(int pageNo, int pageSize, OrderBy... orders) {
        return dao.getPage(pageNo, pageSize, orders);
    }

    /**
     * 根据实体对象获取分页数据
     * @param t 实体
     * @param pageNo 页号
     * @param pageSize 条数
     * @param orders 排序规则
     * @return 分页数据
     */
    public Pagination getPageByT(T t, int pageNo, int pageSize, OrderBy... orders) {
        return dao.getPageByT(t, pageNo, pageSize, orders);
    }

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
    public Pagination getPageByT(T t, int pageNo, int pageSize, boolean anywhere, Condition[] conditions, String... exclude) {
        return dao.getPageByT(t, pageNo, pageSize, anywhere, conditions, exclude);
    }

    /**
     * 获取总记录数
     * @return 总记录数
     */
    public int getCount() {
        return dao.getCount();
    }

    /**
     * 获取记录数
     * @param t 实体对象
     * @return 记录数
     */
    public int getCountByT(T t) {
        return dao.getCountByT(t);
    }

    /**
     * 获取记录数
     * @param t 实体对象
     * @param anyWhere 是否模糊匹配
     * @return 记录数
     */
    public int getCountByT(T t, boolean anyWhere) {
        return dao.getCountByT(t, anyWhere);
    }

    /**
     * 根据一个属性获取记录数
     * @param property 属性
     * @param value 值
     * @return 记录数
     */
    public int getCountByProperty(String property, Object value) {
        return dao.getCountByProperty(property, value);
    }

    /**
     * 按属性查找对象列表
     * @param property 属性
     * @param value 值
     * @return 结果列表
     */
    public List<T> getByProperty(String property, Object value) {
        return dao.getByProperty(property, value);
    }

    /**
     * 按属性查找唯一对象
     * @param property 属性
     * @param value 值
     * @return 结果
     */
    public T getUniqueByProperty(String property, Object value) {
        return dao.getUniqueByProperty(property, value);
    }

}
