package com.syd.dbeaver.driver;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DriverDescriptor {
    private final String name;
    private final String driverClassName;
    private final List<Path> libraries;
    private final ClassLoader rootClassLoader;

    private Class<? extends Driver> driverClass;
    private boolean isLoaded;
    private Driver driverInstance;
    private DriverClassLoader classLoader;

    public DriverDescriptor(
            String name,
            String driverClassName,
            List<Path> libraries
    ) {
        this.name = name;
        this.driverClassName = driverClassName;
        this.libraries = libraries;
        this.rootClassLoader = this.getClass().getClassLoader();
    }

    @NotNull
    public List<Path> getDriverLibraries() {
        return libraries;
    }

    @Nullable
    public DriverClassLoader getClassLoader() {
        return classLoader;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public <T extends Driver> T getDriverInstance() {
        if (driverInstance == null) {
            loadDriver();
        }
        if (isInternalDriver() && driverInstance == null) {
            return (T) createDriverInstance();
        }
        return (T) driverInstance;
    }

    /**
     * Driver shipped along with JDK/DBeaver, doesn't need any additional libraries. Basically it is ODBC driver.
     */
    public boolean isInternalDriver() {
        return driverClassName != null && driverClassName.contains("sun.jdbc");
    }

    private Driver createDriverInstance() throws RuntimeException {
        try {
            return driverClass.getConstructor().newInstance();
        } catch (InstantiationException ex) {
            throw new RuntimeException("Can't instantiate driver class", ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Illegal access", ex);
        } catch (ClassCastException ex) {
            throw new RuntimeException("Bad driver class name specified", ex);
        } catch (Throwable ex) {
            throw new RuntimeException("Error during driver instantiation", ex);
        }
    }

    public void loadDriver() {
        this.loadDriver(false);
    }

    public void loadDriver(boolean forceReload) throws RuntimeException {
        if (isLoaded && !forceReload) {
            return;
        }
        isLoaded = false;
        loadLibraries();

        try {
            try {
                // Load driver classes into core module using plugin class loader
                //noinspection unchecked
                driverClass = (Class<? extends Driver>) Class.forName(driverClassName, true, classLoader);
            } catch (Throwable ex) {
                throw new RuntimeException("""
                        Error creating driver %s instance.
                        Most likely required jar files are missing.
                        You should configure jars in driver settings.
                        
                        Reason: can't load driver class '%s'"""
                        .formatted(getName(), driverClassName),
                        ex);
            }

            // Create driver instance
            /*if (!this.isInternalDriver())*/
            {
                driverInstance = createDriverInstance();
            }

            isLoaded = true;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    private void loadLibraries() {
        this.classLoader = null;

        List<URL> libraryURLs = new ArrayList<>();
        // Load libraries
        for (Path file : libraries) {
            URL url;
            try {
                url = file.toUri().toURL();
            } catch (MalformedURLException e) {
                log.error(e.getMessage(), e);
                continue;
            }
            libraryURLs.add(url);
        }
        // Make class loader
        this.classLoader = new DriverClassLoader(
                this,
                libraryURLs.toArray(new URL[0]),
                rootClassLoader);
    }

    @NotNull
    public String getName() {
        return name;
    }
}
