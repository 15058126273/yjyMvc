package com.yjy.test.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

@Repository
public abstract class BaseDaoImpl<T extends BaseEntity> extends BaseClass implements BaseDao<T> {

    private static final Logger log = LogManager.getLogger(BaseDaoImpl.class);

    protected SessionFactory sessionFactory;
    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public BaseDaoImpl() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        this.persistentClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 获取session会话
     * @return session
     */
    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    /**
     * 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
     */
    protected Criteria createCriteria(Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    public T save(T entity) {
        getSession().save(entity);
        return entity;
    }

    public T update(T entity) {
        getSession().update(entity);
        return entity;
    }

    public T delete(T entity) {
        getSession().delete(entity);
        return entity;
    }

    public T get(Serializable id) {
        return getSession().get(getPersistentClass(), id);
    }

    public T load(Serializable id) {
        return getSession().load(getPersistentClass(), id);
    }

    public T saveOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
        return entity;
    }
}
