///*
// * DBeaver - Universal Database Manager
// * Copyright (C) 2010-2024 DBeaver Corp and others
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.syd.dbeaver.driver;
//
//import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.NotNull;
//
///**
// * DriverLibraryAbstract
// */
//@Slf4j
//public abstract class DriverLibraryAbstract implements DBPDriverLibrary {
//    protected final DriverDescriptor driver;
//    protected final FileType type;
//    protected String path;
//    private boolean optional;
//    protected boolean custom;
//    protected boolean disabled;
//
//    public static DriverLibraryAbstract createFromPath(
//            DriverDescriptor driver, FileType type, String path, String preferredVersion) {
//        return new DriverLibraryLocal(driver, type, path);
//    }
//
//    protected DriverLibraryAbstract(
//            DriverDescriptor driverDescriptor, @NotNull DriverLibraryAbstract copyFrom) {
//        this.driver = driverDescriptor;
//        this.type = copyFrom.type;
//        this.path = copyFrom.path;
//        this.optional = copyFrom.optional;
//        this.custom = copyFrom.custom;
//        this.disabled = copyFrom.disabled;
//    }
//
//    protected DriverLibraryAbstract(DriverDescriptor driver, FileType type, String path) {
//        this.driver = driver;
//        this.type = type;
//        this.path = path;
//        this.custom = true;
//    }
//
//    public DriverDescriptor getDriver() {
//        return driver;
//    }
//
//    @Override
//    public String getVersion() {
//        return null;
//    }
//
//    @NotNull
//    @Override
//    public FileType getType() {
//        return type;
//    }
//
//    @NotNull
//    @Override
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    @Override
//    public String getDescription() {
//        return null;
//    }
//
//    public void setCustom(boolean custom) {
//        this.custom = custom;
//    }
//
//    @Override
//    public boolean isDisabled() {
//        return disabled;
//    }
//
//    public void setDisabled(boolean disabled) {
//        this.disabled = disabled;
//    }
//
//    @Override
//    public String toString() {
//        return getDisplayName();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof DriverLibraryAbstract && ((DriverLibraryAbstract) obj).getId().equals(getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getId().hashCode();
//    }
//
//    public abstract DBPDriverLibrary copyLibrary(DriverDescriptor driverDescriptor);
//}
