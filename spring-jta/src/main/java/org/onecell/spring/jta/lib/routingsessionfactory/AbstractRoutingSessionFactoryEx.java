package org.onecell.spring.jta.lib.routingsessionfactory;

import org.hibernate.*;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;
import org.onecell.spring.jta.lib.routingsessionfactory.AbstractRoutingSessionFactory;

import javax.annotation.Nullable;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractRoutingSessionFactoryEx extends AbstractRoutingSessionFactory {

    @Override
    public SessionFactoryOptions getSessionFactoryOptions() {
        return determineTargetSessionFactory().getSessionFactoryOptions();
    }

    @Override
    public SessionBuilder withOptions() {
        return determineTargetSessionFactory().withOptions();
    }

    @Override
    public Session openSession() throws HibernateException {
        return determineTargetSessionFactory().openSession();
    }

    @Override
    public StatelessSessionBuilder withStatelessOptions() {
        return determineTargetSessionFactory().withStatelessOptions();
    }

    @Override
    public StatelessSession openStatelessSession() {
        return determineTargetSessionFactory().openStatelessSession();
    }

    @Override
    public StatelessSession openStatelessSession(Connection connection) {
        return determineTargetSessionFactory().openStatelessSession(connection);
    }

    @Override
    public Statistics getStatistics() {
        return determineTargetSessionFactory().getStatistics();
    }

    @Override
    public void close() throws HibernateException {
        determineTargetSessionFactory().close();
    }

    @Override
    public Map<String, Object> getProperties() {
        return     determineTargetSessionFactory().getProperties();
    }

    @Override
    public boolean isClosed() {
        return     determineTargetSessionFactory().isClosed();
    }

    @Override
    public Cache getCache() {
        return     determineTargetSessionFactory().getCache();
    }

    @Override
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return     determineTargetSessionFactory().getPersistenceUnitUtil();
    }

    @Override
    public void addNamedQuery(String name, javax.persistence.Query query) {
             determineTargetSessionFactory().addNamedQuery(name,query);
    }

    @Override
    public <T> T unwrap(Class<T> cls) {
        return determineTargetSessionFactory().unwrap(cls);
    }

    @Override
    public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
        determineTargetSessionFactory().addNamedEntityGraph(graphName,entityGraph);
    }

    @Override
    public Set getDefinedFilterNames() {
        return    determineTargetSessionFactory().getDefinedFilterNames();
    }

    @Override
    public FilterDefinition getFilterDefinition(String filterName) throws HibernateException {
        return    determineTargetSessionFactory().getFilterDefinition(filterName);
    }

    @Override
    public boolean containsFetchProfileDefinition(String name) {
        return    determineTargetSessionFactory().containsFetchProfileDefinition(name);
    }

    @Override
    public TypeHelper getTypeHelper() {
        return    determineTargetSessionFactory().getTypeHelper();
    }

    @Override
    public ClassMetadata getClassMetadata(Class entityClass) {
        return    determineTargetSessionFactory().getClassMetadata(entityClass);
    }

    @Override
    public ClassMetadata getClassMetadata(String entityName) {
        return    determineTargetSessionFactory().getClassMetadata(entityName);
    }

    @Override
    public CollectionMetadata getCollectionMetadata(String roleName) {
        return    determineTargetSessionFactory().getCollectionMetadata(roleName);
    }

    @Override
    public Map<String, ClassMetadata> getAllClassMetadata() {
        return    determineTargetSessionFactory().getAllClassMetadata();
    }

    @Override
    public Map getAllCollectionMetadata() {
        return    determineTargetSessionFactory().getAllCollectionMetadata();
    }

    @Override
    public Reference getReference() throws NamingException {
        return    determineTargetSessionFactory().getReference();
    }

    @Override
    public <T> List<EntityGraph<? super T>> findEntityGraphsByType(Class<T> entityClass) {
        return    determineTargetSessionFactory().findEntityGraphsByType(entityClass);
    }

    @Override
    public EntityManager createEntityManager() {
        return    determineTargetSessionFactory().createEntityManager();
    }

    @Override
    public EntityManager createEntityManager(Map map) {
        return    determineTargetSessionFactory().createEntityManager(map);
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType) {
        return determineTargetSessionFactory().createEntityManager(synchronizationType);
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
        return determineTargetSessionFactory().createEntityManager(synchronizationType,map);
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return determineTargetSessionFactory().getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return determineTargetSessionFactory().getMetamodel();
    }

    @Override
    public boolean isOpen() {
        return determineTargetSessionFactory().isOpen();
    }
}
