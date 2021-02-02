package org.onecellboy.db.utils;


import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientConnectionException;

public class SQLExceptionUtil {
    public static boolean isConstraintViolationException(Throwable e)
    {
        if(e==null)
        {
            return false;
        }

        if(e instanceof ConstraintViolationException)
        {
            return true;
        }else if(e instanceof SQLIntegrityConstraintViolationException)
        {
            return true;
        }

        return isConstraintViolationException(e.getCause());
    }

    public static boolean isConnectException(Throwable e)
    {
        if(e==null)
        {
            return false;
        }

        if(e instanceof JDBCConnectionException)
        {
            return true;
        }else if(e instanceof SQLNonTransientConnectionException)
        {
            return true;
        }

        return isConnectException(e.getCause());

    }

}
