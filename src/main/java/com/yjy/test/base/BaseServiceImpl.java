package com.yjy.test.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

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

}
