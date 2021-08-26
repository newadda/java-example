package org.onecell.spring.jta.lib.routingsessionfactory;

import org.springframework.dao.NonTransientDataAccessException;

///// package org.springframework.data.cassandra.core.cql.session.lookup.SessionFactoryLookupFailureException 포팅
@SuppressWarnings("serial")
public class SessionFactoryLookupFailureException  extends NonTransientDataAccessException {

    /**
     * Create a new {@link SessionFactoryLookupFailureException}.
     *
     * @param msg the detail message.
     */
    public SessionFactoryLookupFailureException(String msg) {
        super(msg);
    }

    /**
     * Create a new {@link SessionFactoryLookupFailureException}.
     *
     * @param msg the detail message.
     * @param cause the root cause (usually from using a underlying lookup API).
     */
    public SessionFactoryLookupFailureException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
