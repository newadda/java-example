package org.onecell.spring.jta.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.onecell.spring.jta.entity.Test01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;

@Repository
public class TestRepository {
/*
    private @Nullable
    EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;

    }
      @Nullable
    public EntityManager getEntityManager() {
        return entityManager;
    }

    */

    EntityManagerFactory entityManagerFactory;
    @Autowired
    public TestRepository(EntityManagerFactory entityManagerFactory)
    {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
    }


    public List<Test01> list()
    {
        EntityManager entityManager = getEntityManager();
        int i = getEntityManager().hashCode();
        Query from_test01 = getEntityManager().createQuery("FROM Test01");
        List resultList = from_test01.getResultList();

        return resultList;
    }

    public void save(Test01 entity)
    {
        getEntityManager().unwrap(Session.class).saveOrUpdate(entity);
    }

}
