/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2024 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.syd.dbeaver.driver;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

@Slf4j
public class JDBCConnectionOpener implements PrivilegedExceptionAction<Connection> {
    //private static final Log log = Log.getLog(JDBCConnectionOpener.class);

    private final DriverDescriptor descriptor;
    private final Driver driverInstance;
    private final String url;
    private final Properties connectProps;

    public JDBCConnectionOpener(
            @NotNull DriverDescriptor descriptor,
            @Nullable Driver driverInstance,
            @NotNull String url,
            @NotNull Properties connectProps
    ) {
        this.descriptor = descriptor;
        this.driverInstance = driverInstance;
        this.url = url;
        this.connectProps = connectProps;
    }


    @Override
    @jakarta.annotation.Nullable
    public Connection run() throws Exception {
        // Set context class loaded to driver class loader
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(descriptor.getClassLoader());
        try {
            // Reset DriverManager cache
            try {
                Field driversInitializedField = DriverManager.class.getDeclaredField("driversInitialized");
                driversInitializedField.setAccessible(true);
                driversInitializedField.set(null, false);
            } catch (Throwable e) {
                // Just ignore it
                log.trace(e.getMessage(), e);
            }
            // Open connection
//            if (driverInstance == null) {
                return DriverManager.getConnection(url, connectProps);
//            } else {
//                return driverInstance.connect(url, connectProps);
//            }
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
    }
}
