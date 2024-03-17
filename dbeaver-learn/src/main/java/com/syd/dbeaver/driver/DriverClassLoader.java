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

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

/**
 * DriverClassLoader
 */
public class DriverClassLoader extends URLClassLoader {
    private final DriverDescriptor driver;

    public DriverClassLoader(DriverDescriptor driver, URL[] urls, ClassLoader parent) {
        super(urls, parent);
        this.driver = driver;
    }

    @Override
    protected String findLibrary(String libname) {
        String nativeName = System.mapLibraryName(libname);
        for (var driverFile : driver.getDriverLibraries()) {
            if (driverFile != null && Files.exists(driverFile)) {
                if (Files.isDirectory(driverFile)) {
                    driverFile = driverFile.resolve(nativeName);
                }
                if (driverFile.getFileName().toString().equalsIgnoreCase(nativeName)) {
                    return driverFile.toAbsolutePath().toString();
                }
            }
        }
        return super.findLibrary(libname);
    }
}
