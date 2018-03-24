package com.yjy.test.base;

import java.io.Serializable;

public interface BaseService<T extends BaseEntity> {

    T save(T entity);

    T update(T entity);

    T delete(T entity);

    T get(Serializable id);

    T load(Serializable id);

    T saveOrUpdate(T entity);

}
