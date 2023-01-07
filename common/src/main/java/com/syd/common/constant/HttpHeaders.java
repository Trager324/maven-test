package com.syd.common.constant;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hpsf.ClassIDPredefined;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author songyide
 * @date 2022/9/23
 * @see ClassIDPredefined
 */
public class HttpHeaders {
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_XLS = "application/vnd.ms-excel";
    public static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String CONTENT_TYPE_DOC = "application/msword";
    public static final String CONTENT_TYPE_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    public static void setExportHeader(HttpServletResponse rsp, ExportTypeEnum et, String fileName) {
        String suffix = et.getSuffix();
        if (!fileName.endsWith(suffix)) {
            fileName = fileName + suffix;
        }
        String encoded = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        rsp.setContentType(et.getContentType());
        rsp.setCharacterEncoding(StringPool.UTF_8);
        rsp.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + encoded);
    }
}
