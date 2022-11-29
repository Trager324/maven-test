package com.syd.common.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author songyide
 * @date 2022/9/23
 */
@Getter
public enum ExportTypeEnum {
    /**
     * excel，默认xlsx
     */
    EXCEL(HttpHeaders.CONTENT_TYPE_EXCEL, ".xlsx", ".xls"),
    /**
     * word，默认docx
     */
    WORD(HttpHeaders.CONTENT_TYPE_WORD, ".docx", ".doc"),
    ;
    private final List<String> suffixes;
    private final String contentType;

    ExportTypeEnum(String contentType, String... suffixes) {
        this.contentType = contentType;
        this.suffixes = Collections.unmodifiableList(Arrays.asList(suffixes));
    }
}
