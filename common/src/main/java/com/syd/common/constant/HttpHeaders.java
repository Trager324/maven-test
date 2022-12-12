package com.syd.common.constant;

import com.syd.common.exception.BaseException;

import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author songyide
 * @date 2022/9/23
 */
public class HttpHeaders {
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json;";
    public static final String CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";
    public static final String CONTENT_TYPE_WORD = "application/vnd.ms-word";

    public static void setExportHeader(HttpServletResponse rsp, ExportTypeEnum et, String fileName) {
        try {
            List<String> suffixes = et.getSuffixes();
            String fileSuffix = null;
            for (String suffix : suffixes) {
                while (fileName.endsWith(suffix)) {
                    if (fileSuffix == null) {
                        fileSuffix = suffix;
                    }
                    fileName = fileName.substring(0, fileName.length() - suffix.length());
                }
            }
            if (fileSuffix == null) {
                fileSuffix = suffixes.get(0);
            }
            String encoded = URLEncoder.encode(fileName + fileSuffix, StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20");

            rsp.setContentType(et.getContentType());
            rsp.setCharacterEncoding(Constants.UTF_8);
            rsp.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + encoded);
        } catch (Exception e) {
            throw BaseException.of(ResponseCode.B0001, e);
        }
    }
}
