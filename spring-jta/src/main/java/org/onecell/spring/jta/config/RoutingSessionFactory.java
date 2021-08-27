package org.onecell.spring.jta.config;

import org.onecell.spring.jta.lib.DBKeyContext;
import org.onecell.spring.jta.lib.routingsessionfactory.AbstractRoutingSessionFactoryEx;

import javax.annotation.Nullable;

public class RoutingSessionFactory extends AbstractRoutingSessionFactoryEx {
    DBKeyContext<String> context ;

    public RoutingSessionFactory(DBKeyContext<String> context) {
        this.context = context;
    }

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return context.get();
    }
}
