package com.syd.dbeaver;

import com.syd.dbeaver.driver.DriverDescriptor;
import com.syd.dbeaver.driver.JDBCConnectionOpener;
import com.syd.dbeaver.driver.PostgresqlDriverConstants;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Slf4j
public class Main {
    public static final String JDBC_URL_POSTGRESQL_LOCAL = "jdbc:postgresql://localhost:5432/postgres";

    public static void testPostgresqlConnection(Connection conn) throws SQLException {
        try (var stmt = conn.prepareStatement("select 1");
             var rs = stmt.executeQuery();) {
            rs.next();
            log.info("{}", rs.getInt(1));
            var metaData = conn.getMetaData();
            log.info(metaData.getDriverName());
            log.info("{}", conn.getClientInfo());
        }
    }

    public static void main(String[] args) throws Exception {
        var desc_42_7_3 = new DriverDescriptor(
                PostgresqlDriverConstants.DATABASE_POSTGRESQL,
                PostgresqlDriverConstants.DRIVER_NAME,
                List.of(PostgresqlDriverConstants.DRIVER_PATH_PG_42_7_3)
        );
        var desc_42_5_6 = new DriverDescriptor(
                PostgresqlDriverConstants.DATABASE_POSTGRESQL,
                PostgresqlDriverConstants.DRIVER_NAME,
                List.of(PostgresqlDriverConstants.DRIVER_PATH_PG_42_5_6)
        );
        Properties props = new Properties();
        props.put("user", "postgres");
        props.put("password", "root");
        props.put("ssl", "false");
        var opener1 = new JDBCConnectionOpener(desc_42_5_6, desc_42_5_6.getDriverInstance(),
                JDBC_URL_POSTGRESQL_LOCAL, props);
        var opener2 = new JDBCConnectionOpener(desc_42_7_3, desc_42_7_3.getDriverInstance(),
                JDBC_URL_POSTGRESQL_LOCAL, props);
        log.info("{}", desc_42_5_6.getDriverInstance().getMinorVersion());
        log.info("{}", desc_42_7_3.getDriverInstance().getMinorVersion());
        try (Connection c1 = opener1.run();
             Connection c2 = opener2.run()) {
            testPostgresqlConnection(c1);
            testPostgresqlConnection(c2);
        }
    }
}