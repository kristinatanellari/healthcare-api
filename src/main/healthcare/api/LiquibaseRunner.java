package main.healthcare.api;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

public class LiquibaseRunner {

    public static void main(String[] args) throws Exception {
        String changeLogFile = "classpath:/db/changelog/db.changelog-master.xml";
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "ina251299";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Liquibase liquibase = new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(),
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection)));
            liquibase.update("");
        }
    }
}
