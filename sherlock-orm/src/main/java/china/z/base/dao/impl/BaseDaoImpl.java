package china.z.base.dao.impl;


import china.z.base.dao.IBaseDao;
import china.z.util.PageResults;
import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseDaoImpl<T> implements IBaseDao<T> {

    private final static String SORTED_DESC = "DESC";
    private final static String SORTED_ASC = "ASC";

    private SessionFactory sessionFactory;
    private Class<T> entityClass;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 开启操作数据库Session会话
     *
     * @return
     */
    private Session getSession() {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    /**
     * 关闭操作数据库Session会话
     *
     * @param session
     */
    private void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    /**
     * 无参构造方法
     */
    public BaseDaoImpl() {
        super();
        if (entityClass == null) {
            this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }

    /**
     * 有参构造方法
     *
     * @param entityClass
     */
    public BaseDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * 创建Hql query
     *
     * @param hql
     * @param values
     * @return
     */
    private Query createHqlQuerys(String hql, Object... values) {
        Query query = getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 创建Sql query
     *
     * @param sql
     * @param values
     * @return
     */
    private Query createSqlQuerys(String sql, Object... values) {
        Query query = getSession().createSQLQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 保存对象
     *
     * @param table
     */
    public void save(T table) {
        getSession().save(table);
    }

    /**
     * 保存或更新对象
     *
     * @param table
     */
    public void saveOrUpdate(T table) {
        getSession().saveOrUpdate(table);
    }

    /**
     * 加载实体对象
     *
     * @param uuid
     * @return
     */
    public T load(String uuid) {
        return (T) getSession().load(entityClass, uuid);
    }

    /**
     * 查找实体对象
     *
     * @param uuid
     * @return
     */
    public T get(String uuid) {
        return (T) getSession().get(entityClass, uuid);
    }

    /**
     * 是否包含对象
     *
     * @param table
     * @return
     */
    public boolean contains(T table) {
        return getSession().contains(table);
    }

    /**
     * 删除对象
     *
     * @param table
     */
    public void delete(T table) {
        getSession().delete(table);
    }

    /**
     * 根据UUID删除对象
     *
     * @param uuid
     */
    public boolean deleteByPK(String uuid) {
        T table = get(uuid);
        if (table == null) {
            return false;
        }
        delete(table);
        return true;
    }

    /**
     * 执行Hql语句
     *
     * @param hql
     * @param values
     */
    public void executeHql(String hql, Object... values) {
        Query query = createHqlQuerys(hql, values);
        query.executeUpdate();
    }

    /**
     * 执行Sql语句
     *
     * @param sql
     * @param values
     */
    public void executeSql(String sql, Object... values) {
        Query query = createSqlQuerys(sql, values);
        query.executeUpdate();
    }

    /**
     * 根据Hql查找唯一实体
     *
     * @param hql
     * @param values
     * @return
     */
    public T getByHql(String hql, Object... values) {
        Query query = createHqlQuerys(hql, values);
        return (T) query.uniqueResult();
    }

    /**
     * 根据Hql查找列表
     *
     * @param hql
     * @param values
     * @return
     */
    public List<T> getListByHql(String hql, Object... values) {
        Query query = createSqlQuerys(hql, values);
        return query.list();
    }

    /**
     * 根据Sql查找唯一实体
     *
     * @param sql
     * @param values
     * @return
     */
    public Map getBySql(String sql, Object... values) {
        Query query = createSqlQuerys(sql, values);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        Object result = query.uniqueResult();
        return (Map) result;
    }

    /**
     * 根据Sql查找列表
     *
     * @param sql
     * @param values
     * @return
     */
    public List<Map> getListBySql(String sql, Object... values) {
        Query query = createSqlQuerys(sql, values);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> listMap = query.list();
        return listMap;
    }

    /**
     * 刷新对象
     *
     * @param table
     */
    public void refresh(T table) {
        getSession().refresh(table);
    }

    /**
     * 修改对象
     *
     * @param table
     */
    public void update(T table) {
        getSession().update(table);
    }

    /**
     * 根据Hql语句得到记录数
     *
     * @param hql
     * @param values
     * @return
     */
    public Long countByHql(String hql, Object... values) {
        Query query = createHqlQuerys(hql, values);
        return (Long) query.uniqueResult();
    }

    /**
     * 分页
     *
     * @param hql
     * @param countHql
     * @param pageNo
     * @param pageSize
     * @param values
     * @return
     */
    public PageResults<T> findPageByFetchedHql(String hql, String countHql, int pageNo, int pageSize, Object... values) {
        PageResults<T> pageResults = new PageResults<T>();
        Query query = createHqlQuerys(hql, values);
        int currentPage = pageNo > 1 ? pageNo : 1;
        pageResults.setCurrentPage(currentPage);
        pageResults.setPageSize(pageSize);
        if (StringUtils.isEmpty(countHql)) {
            ScrollableResults results = query.scroll();
            results.last();
            pageResults.setTotalCount(results.getRowNumber() + 1);//设置总记录数
        } else {
            Long count = countByHql(countHql, values);
            pageResults.setTotalCount(count.intValue());
        }
        pageResults.resetPageNo();
        List<T> itemList = query.setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize).list();
        if (itemList == null) {
            itemList = new ArrayList<T>();
        }
        pageResults.setResults(itemList);
        return pageResults;
    }
}
