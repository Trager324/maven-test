package com.syd.dbeaver.driver;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class PostgresqlDriverConstants {
    private static final Path BASIC_PATH = Path.of("/Users/songyd/.m2/repository/org/postgresql/postgresql");
    public static final Path DRIVER_PATH_PG_42_7_3 = BASIC_PATH.resolve("42.7.3").resolve("postgresql-42.7.3.jar");
    public static final Path DRIVER_PATH_PG_42_6_2 = BASIC_PATH.resolve("42.6.2").resolve("postgresql-42.6.2.jar");
    public static final Path DRIVER_PATH_PG_42_5_6 = BASIC_PATH.resolve("42.5.6").resolve("postgresql-42.5.6.jar");

    public static final String DATABASE_POSTGRESQL = "PostgreSQL";
    public static final String DRIVER_NAME = "org.postgresql.Driver";

    static {
        if (!Files.isDirectory(BASIC_PATH)) {
            throw new RuntimeException("Library not exists: " + BASIC_PATH);
        }
    }
}
