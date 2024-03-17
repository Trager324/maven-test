///*
// * Copyright (C) 2010-2012 Serge Rieder
// * serge@jkiss.org
// *
// * This library is free software; you can redistribute it and/or
// * modify it under the terms of the GNU Lesser General Public
// * License as published by the Free Software Foundation; either
// * version 2.1 of the License, or (at your option) any later version.
// *
// * This library is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// * Lesser General Public License for more details.
// *
// * You should have received a copy of the GNU Lesser General Public
// * License along with this library; if not, write to the Free Software
// * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
// */
//
//package com.syd.dbeaver.driver;
//
//
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.nio.file.Path;
//
///**
// * DBPDriver local path
// */
//public interface DBPDriverLibrary {
//
//    /**
//     * Driver file type
//     */
//    enum FileType {
//        jar,
//        lib,
//        executable,
//        license;
//
//        public static FileType getFileTypeByFileName(String fileName) {
//            return fileName.endsWith(".jar") || fileName.endsWith(".zip")
//                    ? DBPDriverLibrary.FileType.jar
//                    : DBPDriverLibrary.FileType.lib;
//        }
//    }
//
//    @NotNull
//    String getDisplayName();
//
//    /**
//     * Library native id.
//     * Id doesn't include version information so the same libraries with different versions have the same id.
//     */
//    @NotNull
//    String getId();
//
//    /**
//     * Library version. If library doesn't support versions returns null.
//     */
//    @Nullable
//    String getVersion();
//
//    @NotNull
//    FileType getType();
//
//    /**
//     * Native library URI.
//     * Could be a file path or maven artifact references or anything else.
//     */
//    @NotNull
//    String getPath();
//
//    @Nullable
//    String getDescription();
//
//    boolean isDisabled();
//
//    @Nullable
//    Path getLocalFile();
//
//}
