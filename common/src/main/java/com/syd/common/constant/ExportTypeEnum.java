package com.syd.common.constant;

import lombok.Getter;

/**
 * @author songyide
 * @date 2022/9/23
 */
@Getter
public enum ExportTypeEnum {
    /**
     * excel，默认xlsx
     */
    XLS(HttpHeaders.CONTENT_TYPE_XLS),
    XLSX(HttpHeaders.CONTENT_TYPE_XLSX),
    /**
     * word，默认docx
     */
    DOC(HttpHeaders.CONTENT_TYPE_DOC),
    DOCX(HttpHeaders.CONTENT_TYPE_DOCX),
    ;
    private final String suffix;
    private final String contentType;

    ExportTypeEnum(String contentType) {
        this.contentType = contentType;
        this.suffix = StringPool.DOT + name().toLowerCase();
    }
}
