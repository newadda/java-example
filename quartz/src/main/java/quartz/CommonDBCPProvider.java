package quartz;

import org.quartz.utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @see org.quartz.utils.C3p0PoolingConnectionProvider 를 참고해라.
 */
public class CommonDBCPProvider implements ConnectionProvider {
    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public void shutdown() throws SQLException {

    }

    @Override
    public void initialize() throws SQLException {

    }

    // 속성
    public void setPropertyOne(String propertyOne)
    {

    }
}
