package org.istech.repository;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public class ACommonQuerydslRepositorySupport  {

    private @Nullable
    EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager=entityManager;

    }

    public JPAQueryFactory getJpaQueryFactory() {
        return new JPAQueryFactory(getEntityManager());
    }

    public Session getSession()
    {
        return  getEntityManager().unwrap(Session.class);
    }


    @Nullable
    protected EntityManager getEntityManager() {
        return entityManager;
    }



    /**
     * Returns the underlying Querydsl helper instance.
     *
     * @return
     */
    @Nullable
    protected Querydsl getQuerydsl(Class<?> domainClass) {
        PathBuilder<?> builder = new PathBuilderFactory().create(domainClass);
        Querydsl querydsl = new Querydsl(entityManager, builder);
        return querydsl;
    }



    public <T,ID extends Serializable> T findById(Class<T> clazz,ID id)
    {
        Session entityManager = getSession();
        T entity = entityManager.find(clazz, id);
        return entity;
    }

    public  void update(Object entity)
    {
        Session entityManager = getSession();
        entityManager.update(entity);
    }

    public void merge(Object entity) {
        Session entityManager = getSession();
        entityManager.merge(entity);
    }



    public <T,ID extends Serializable> void deleteById(Class<T> clazz,ID id)
    {

        Session entityManager =getSession();
        T byId = entityManager.find(clazz, id);
        if(byId==null)
        {
            return ;
        }
        entityManager.delete(byId);
    }


    public void delete(Object entity) {
        Session entityManager = getSession();
        if(entity!=null)
        {
            entityManager.delete(entity);
        }
    }

    public Serializable save(Object entity)
    {
        Session entityManager =getSession();
        Serializable save = entityManager.save(entity);
        return save;
    }

    public void saveOrUpdate(Object entity)
    {
        Session entityManager =getSession();
        entityManager.saveOrUpdate(entity);
    }

    public void flush()
    {
        Session entityManager =getSession();
        entityManager.flush();;
    }




}
