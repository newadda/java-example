package org.onecell.spring.jta.lib.routingsessionfactory;

import org.hibernate.SessionFactory;

///// package org.springframework.data.cassandra.core.cql.session.lookup.SessionFactoryLookup 포팅
@FunctionalInterface
public interface SessionFactoryLookup {
    /**
     * Implementations must implement this method to retrieve the {@link SessionFactory} identified by the given name from
     * their backing store.
     *
     * @param sessionFactoryName the name of the {@link SessionFactory}.
     * @return the {@link SessionFactory} (never {@literal null}).
     * @throws SessionFactoryLookupFailureException if the lookup failed.
     */
    SessionFactory getSessionFactory(String sessionFactoryName) throws SessionFactoryLookupFailureException;
}
