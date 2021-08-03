package org.istech.repository;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.Session;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public  abstract class AQuerydslRepositorySupport<T,ID extends Serializable> extends QuerydslRepositorySupport  {

    public AQuerydslRepositorySupport(Class<T> domainClass) {
        super((Class<T>)domainClass);
    }


    @Override
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);

    }

    public JPAQueryFactory getJpaQueryFactory() {
        return new JPAQueryFactory(getEntityManager());
    }

    public Session getSession()
    {
        return  getEntityManager().unwrap(Session.class);
    }

    @Override
    protected <T> PathBuilder<T> getBuilder() {
        return super.getBuilder();
    }

    public List<T> listAll()
    {
        JPAQueryFactory jpaQueryFactory = getJpaQueryFactory();
        JPAQuery<?> from = jpaQueryFactory.from(getBuilder());
        JPAQuery<T> select = from.select(getBuilder());
        List<T> fetch = select.fetch();
        return fetch;

    }

    public T findById(ID id)
    {
        Session entityManager = getSession();
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T entity = entityManager.find(clazz, id);
        return entity;
    }

    public void update(T entity)
    {
        Session entityManager = getSession();
        entityManager.update(entity);
    }

    public void merge(T entity) {
        Session entityManager = getSession();
        entityManager.merge(entity);
    }



    public void deleteById(ID id)
    {
        Session entityManager =getSession();
        T byId = findById(id);
        if(byId==null)
        {
            return ;
        }
        entityManager.delete(byId);
    }


    public void delete(T entity) {
        Session entityManager = getSession();
        if(entity!=null)
        {
            entityManager.delete(entity);
        }
    }

    public ID save(T entity)
    {
        Session entityManager =getSession();
        ID save = (ID)(entityManager.save(entity));
        return save;
    }

    public void saveOrUpdate(T entity)
    {
        Session entityManager =getSession();
        entityManager.saveOrUpdate(entity);
    }

    public void flush()
    {
        Session entityManager =getSession();
        entityManager.flush();;
    }

    public void clear() {
        Session entityManager = getSession();
        entityManager.clear();

    }

    public void flushAndClear() {
        flush();
        clear();
    }

}
