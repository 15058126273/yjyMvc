package com.yjy.test.base;

import com.yjy.test.util.hibernate.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hibernate.EntityMode.POJO;

@Repository
public abstract class BaseDaoImpl<T extends BaseEntity> extends BaseClass implements BaseDao<T> {

    private static final Logger log = LogManager.getLogger(BaseDaoImpl.class);

    private SessionFactory sessionFactory;
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

    public T save(T entity) {
        Assert.notNull(entity, "entity cannot be null...");
        getSession().save(entity);
        return entity;
    }

    public T update(T entity) {
        Assert.notNull(entity, "entity cannot be null...");
        getSession().update(entity);
        return entity;
    }

    public T delete(T entity) {
        Assert.notNull(entity, "entity cannot be null...");
        getSession().delete(entity);
        return entity;
    }

    public T get(Serializable id) {
        Assert.notNull(id, "id cannot be null...");
        return getSession().get(getPersistentClass(), id);
    }

    public T load(Serializable id) {
        Assert.notNull(id, "id cannot be null...");
        return getSession().load(getPersistentClass(), id);
    }

    public T saveOrUpdate(T entity) {
        Assert.notNull(entity, "entity cannot be null...");
        getSession().saveOrUpdate(entity);
        return entity;
    }

    @SuppressWarnings("unchecked")
    public T merge(T entity) {
        Assert.notNull(entity, "entity cannot be null...");
        return (T) getSession().merge(entity);
    }

    public T delete(Serializable id) {
        Assert.notNull(id, "id cannot be null");
        T t = get(id);
        return delete(t);
    }

    public void refresh(T entity) {
        Assert.notNull(entity, "entity cannot be null");
        getSession().refresh(entity);
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll(OrderBy... orders) {
        Criteria crit = createCriteria();
        if (orders != null) {
            for (OrderBy order : orders) {
                crit.addOrder(order.getOrder());
            }
        }
        return crit.list();
    }

    @SuppressWarnings("unchecked")
    public List<T> getList(T entity, OrderBy... orders) {
        Condition[] conditions = null;
        if (orders != null) {
            conditions = Arrays.copyOf(orders, orders.length);
        }
        Criteria criteria = getCritByT(entity, false, conditions);
        return criteria.list();
    }

    public Pagination getPage(int pageNo, int pageSize, OrderBy... orders) {
        Criteria crit = createCriteria();
        return getByCriteria(crit, pageNo, pageSize, null, OrderBy.asOrders(orders));
    }

    public Pagination getPageByT(T entity, int pageNo, int pageSize, OrderBy... orders) {
        Assert.notNull(entity, "entity cannot be null");
        Condition[] conditions = null;
        if (orders != null) {
            conditions = Arrays.copyOf(orders, orders.length);
        }
        return getPageByT(entity, pageNo, pageSize, false, conditions);
    }

    public Pagination getPageByT(T entity, int pageNo, int pageSize, boolean anywhere, Condition[] conditions, String... exclude) {
        Assert.notNull(entity, "entity cannot be null");
        Order[] orderArr = null;
        Condition[] condArr = null;
        if (conditions != null && conditions.length > 0) {
            List<Order> orderList = new ArrayList<>();
            List<Condition> condList = new ArrayList<>();
            for (Condition c : conditions) {
                if (c instanceof OrderBy) {
                    orderList.add(((OrderBy) c).getOrder());
                } else {
                    condList.add(c);
                }
            }
            orderArr = new Order[orderList.size()];
            condArr = new Condition[condList.size()];
            orderArr = orderList.toArray(orderArr);
            condArr = condList.toArray(condArr);
        }
        Criteria crit = getCritByT(entity, anywhere, condArr);
        return getByCriteria(crit, pageNo, pageSize, null, orderArr);
    }

    public int getCount() {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        return ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

    public int getCountByT(T entity) {
        Assert.notNull(entity, "entity cannot be null");
        return getCountByT(entity, false);
    }

    public int getCountByT(T entity, boolean anywhere) {
        Assert.notNull(entity, "entity cannot be null");
        Criteria crit = getCritByT(entity, anywhere, null);
        return ((Number) crit.setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

    public int getCountByProperty(String property, Object value) {
        Assert.hasText(property, "property cannot be blank");
        Assert.notNull(value, "value cannot be null");
        Criteria criteria = createCriteria(Restrictions.eq(property, value)).setProjection(Projections.rowCount());
        return ((Number) criteria.uniqueResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    public List<T> getByProperty(String property, Object value) {
        Assert.hasText(property, "property cannot be blank");
        Assert.notNull(value, "value cannot be null");
        return createCriteria(Restrictions.eq(property, value)).list();
    }

    @SuppressWarnings("unchecked")
    public T getUniqueByProperty(String property, Object value) {
        Assert.hasText(property, "property cannot be blank");
        Assert.notNull(value, "value cannot be null");
        Criteria criteria = createCriteria(Restrictions.eq(property, value));
        return (T) criteria.uniqueResult();
    }

    @SuppressWarnings("rawtypes")
    protected Pagination getByCriteria(Criteria crit, int pageNo, int pageSize, Projection projection, Order... orders) {
        int totalCount = ((Number) crit.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        crit.setProjection(projection);
        if (projection == null) {
            crit.setResultTransformer(Criteria.ROOT_ENTITY);
        }
        if (orders != null) {
            for (Order order : orders) {
                crit.addOrder(order);
            }
        }
        crit.setFirstResult(p.getFirstResult());
        crit.setMaxResults(p.getPageSize());
        p.setList(crit.list());
        return p;
    }

    protected Criteria getCritByT(T bean, boolean anyWhere, Condition[] conds, String... exclude) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        Example example = Example.create(bean).excludeZeroes();
        example.setPropertySelector(NOT_BLANK);
        if (anyWhere) {
            example.enableLike(MatchMode.ANYWHERE);
            example.ignoreCase();
        }
        for (String p : exclude) {
            example.excludeProperty(p);
        }
        crit.add(example);
        // 处理排序和is null字段
        if (conds != null) {
            for (Condition o : conds) {
                if (o instanceof OrderBy) {
                    OrderBy order = (OrderBy) o;
                    crit.addOrder(order.getOrder());
                } else if (o instanceof Nullable) {
                    Nullable isNull = (Nullable) o;
                    if (isNull.isNull()) {
                        crit.add(Restrictions.isNull(isNull.getField()));
                    } else {
                        crit.add(Restrictions.isNotNull(isNull.getField()));
                    }
                    //比较的处理
                } else if (o instanceof CompareBy) {
                    CompareBy compareBy = (CompareBy) o;
                    if (CompareBy.CompareType.LT.equals(compareBy.getCompareType())) {
                        crit.add(Restrictions.lt(compareBy.getField(), compareBy.getFieldValue()));
                    } else if (CompareBy.CompareType.GT.equals(compareBy.getCompareType())) {
                        crit.add(Restrictions.gt(compareBy.getField(), compareBy.getFieldValue()));
                    } else {
                        crit.add(Restrictions.eq(compareBy.getField(), compareBy.getFieldValue()));
                    }
                    //复合的处理（and or）
                } else if (o instanceof MergerBy) {
                    MergerBy mergerBy = (MergerBy) o;
                    CompareBy compareBya = (CompareBy) mergerBy.getConditiona();
                    Criterion criteriona;
                    if (CompareBy.CompareType.LT.equals(compareBya.getCompareType())) {
                        criteriona = Restrictions.lt(compareBya.getField(), compareBya.getFieldValue());
                    } else if (CompareBy.CompareType.GT.equals(compareBya.getCompareType())) {
                        criteriona = Restrictions.gt(compareBya.getField(), compareBya.getFieldValue());
                    } else {
                        criteriona = Restrictions.eq(compareBya.getField(), compareBya.getFieldValue());
                    }
                    CompareBy compareByb = (CompareBy) mergerBy.getConditionb();
                    Criterion criterionb;
                    if (CompareBy.CompareType.LT.equals(compareByb.getCompareType())) {
                        criterionb = Restrictions.lt(compareByb.getField(), compareByb.getFieldValue());
                    } else if (CompareBy.CompareType.GT.equals(compareByb.getCompareType())) {
                        criterionb = Restrictions.gt(compareByb.getField(), compareByb.getFieldValue());
                    } else {
                        criterionb = Restrictions.eq(compareByb.getField(), compareByb.getFieldValue());
                    }
                    if (MergerBy.MergerType.AND.equals(mergerBy.getMergerType())) {
                        crit.add(Restrictions.and(criteriona, criterionb));
                    } else {
                        crit.add(Restrictions.or(criteriona, criterionb));
                    }
                }
            }
        }
        // 处理many to one查询
        ClassMetadata cm = getCmd(bean.getClass());
        String[] fieldNames = cm.getPropertyNames();
        for (String field : fieldNames) {
            Object o = cm.getPropertyValue(bean, field);
            if (o == null) {
                continue;
            }
            ClassMetadata subCm = getCmd(o.getClass());
            if (subCm == null) {
                continue;
            }
            Serializable id = subCm.getIdentifier(o);
            if (id != null) {
                Serializable idName = subCm.getIdentifierPropertyName();
                crit.add(Restrictions.eq(field + "." + idName, id));
            } else {
                crit.createCriteria(field).add(Example.create(o));
            }
        }
        return crit;
    }

    /**
     * 获取session会话
     *
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

    @SuppressWarnings("rawtypes")
    private ClassMetadata getCmd(Class clazz) {
        return sessionFactory.getClassMetadata(clazz);
    }

    private static final NotBlankPropertySelector NOT_BLANK = new NotBlankPropertySelector();

    /**
     * 不为空的EXAMPLE属性选择方式
     *
     * @author liufang
     */
    static final class NotBlankPropertySelector implements Example.PropertySelector {
        private static final long serialVersionUID = 1L;

        public boolean include(Object object, String property, Type type) {
            return object != null && !(object instanceof String && isBlank((String) object));
        }
    }

}
