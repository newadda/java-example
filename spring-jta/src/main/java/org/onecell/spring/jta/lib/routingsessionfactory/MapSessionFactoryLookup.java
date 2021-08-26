package org.onecell.spring.jta.lib.routingsessionfactory;

import org.hibernate.SessionFactory;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/// package org.springframework.data.cassandra.core.cql.session.lookup.MapSessionFactoryLookup 포팅
public class MapSessionFactoryLookup implements SessionFactoryLookup {

    private final Map<String, SessionFactory> sessionFactories = new ConcurrentHashMap<>(4);

    /**
     * Create a new instance of {@link MapSessionFactoryLookup}.
     */
    public MapSessionFactoryLookup() {}

    /**
     * Create a new instance of {@link MapSessionFactoryLookup}.
     *
     * @param sessionFactories the {@link Map} of {@link SessionFactory session factories}. The keys are {@link String
     *          Strings}, the values are actual {@link SessionFactory} instances.
     */
    public MapSessionFactoryLookup(Map<String, SessionFactory> sessionFactories) {
        setSessionFactories(sessionFactories);
    }

    /**
     * Create a new instance of {@link MapSessionFactoryLookup}.
     *
     * @param sessionFactoryName the name under which the supplied {@link SessionFactory} is to be added
     * @param sessionFactory the {@link SessionFactory} to be added
     */
    public MapSessionFactoryLookup(String sessionFactoryName, SessionFactory sessionFactory) {
        addSessionFactory(sessionFactoryName, sessionFactory);
    }

    /**
     * Set the {@link Map} of {@link SessionFactory session factories}; the keys are {@link String Strings}, the values
     * are actual {@link SessionFactory} instances.
     * <p>
     * If the supplied {@link Map} is {@literal null}, then this method call effectively has no effect.
     *
     * @param sessionFactories {@link Map} of {@link SessionFactory session factories}.
     */
    public void setSessionFactories(@Nullable Map<String, SessionFactory> sessionFactories) {
        if (sessionFactories != null) {
            this.sessionFactories.putAll(sessionFactories);
        }
    }

    /**
     * Get the {@link Map} of {@link SessionFactory session factories} maintained by this object.
     * <p>
     * The returned {@link Map} is {@link Collections#unmodifiableMap(java.util.Map) unmodifiable}.
     *
     * @return {@link Map} of {@link SessionFactory session factories}.
     */
    public Map<String, SessionFactory> getSessionFactories() {
        return Collections.unmodifiableMap(this.sessionFactories);
    }

    /**
     * Add the supplied {@link SessionFactory} to the map of {@link SessionFactory session factories} maintained by this
     * object.
     *
     * @param sessionFactoryName the name under which the supplied {@link SessionFactory} is to be added
     * @param sessionFactory the {@link SessionFactory} to be so added
     */
    public void addSessionFactory(String sessionFactoryName, SessionFactory sessionFactory) {

        Assert.notNull(sessionFactoryName, "SessionFactory name must not be null");
        Assert.notNull(sessionFactory, "SessionFactory must not be null");

        this.sessionFactories.put(sessionFactoryName, sessionFactory);
    }

    /* (non-Javadoc)
     * @see org.springframework.data.cassandra.core.cql.session.lookup.SessionFactoryLookup#getSessionFactory(java.lang.String)
     */
    @Override
    public SessionFactory getSessionFactory(String sessionFactoryName) throws SessionFactoryLookupFailureException {

        Assert.notNull(sessionFactoryName, "SessionFactory name must not be null");

        SessionFactory sessionFactory = this.sessionFactories.get(sessionFactoryName);

        if (sessionFactory == null) {
            throw new SessionFactoryLookupFailureException(
                    String.format("No SessionFactory with name [%s] registered", sessionFactoryName));
        }

        return sessionFactory;
    }
}
