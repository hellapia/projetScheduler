package hei.devweb.scheduler.daos;

import java.sql.*;

public class DaoBase {
    protected static Statement createStatement() throws SQLException {
        Connection connection = getConnection();
        return connection.createStatement();
    }

    protected static PreparedStatement prepareStatement(String sql) throws SQLException {
        Connection connection = getConnection();
        return connection.prepareStatement(sql);
    }

    private static Connection getConnection() throws SQLException {
        return DataSourceProvider.getDataSource().getConnection();
    }
}
