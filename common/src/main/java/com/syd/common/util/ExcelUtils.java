package com.syd.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.syd.common.bean.tree.AbstractHeaderCreator;
import com.syd.common.bean.tree.TreeItem;
import com.syd.common.constant.ExportTypeEnum;
import com.syd.common.constant.HttpHeaders;
import com.syd.common.constant.ResponseCode;
import com.syd.common.exception.BaseException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;

/**
 * excel工具类
 *
 * @author songyide
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class ExcelUtils {
    private static final WriteHandler DEFAULT_HANDLER = new RowWriteHandler() {
        @Override
        public void beforeRowCreate(WriteSheetHolder wsh, WriteTableHolder wth, Integer ri, Integer rri, Boolean isHead) {
            if (isHead) {
                Map<Integer, Head> headMap = wsh.getExcelWriteHeadProperty().getHeadMap();
                Map<String, Head> fieldNameHead = new HashMap<>(headMap.size());
                headMap.forEach((k, v) -> fieldNameHead.put(v.getFieldName(), v));
                int index = 0;
                if (!ExtCollectionUtils.isEmpty(wsh.getIncludeColumnFieldNames())) {
                    for (String fieldName : wsh.getIncludeColumnFieldNames()) {
                        Head head = fieldNameHead.get(fieldName);
                        if (head != null) {
                            headMap.put(index++, head);
                        }
                    }
                }
            }
        }
    };

    /**
     * 表头项创建参考{@link AbstractHeaderCreator}
     *
     * @param rsp      响应
     * @param fileName 文件名称(enum枚举）
     * @param clazz    导出的目标类
     * @param items    表头项
     * @param itor     分批获取数据的方法
     */
    public static <T> void export(HttpServletResponse rsp, String fileName, Class<T> clazz,
                                  List<TreeItem> items, Iterator<List<T>> itor) {
        try (ServletOutputStream os = rsp.getOutputStream()) {
            ExcelWriterBuilder builder = EasyExcel.write(os)
                    .excelType(ExcelTypeEnum.XLSX)
                    .registerWriteHandler(DEFAULT_HANDLER);
            // 是否Map
            var isMap = Map.class.isAssignableFrom(clazz);
            var matrix = TreeItem.treeToMatrix(items);
            var header = TreeItem.getExcelHeader(matrix);
            if (isMap) {
                // Map直接根据items构建header
                Objects.requireNonNull(items, "Map类型导出必须提供表头项");
                builder.head(header);
            } else {
                if (!ExtCollectionUtils.isEmpty(items)) {
                    // items不为空首先依据items
                    builder.head(header)
                            .includeColumnFieldNames(items.stream()
                                    .map(TreeItem::key)
                                    .toList());
                } else {
                    // 否则导出所有class中的字段
                    builder.head(clazz);
                }
            }
            List<String> leavesKeys = TreeItem.getLeavesStream(matrix)
                    .map(TreeItem::key)
                    .toList();
            try (ExcelWriter excelWriter = builder.build()) {
                HttpHeaders.setExportHeader(rsp, ExportTypeEnum.XLSX, fileName);
                WriteSheet writeSheet = EasyExcel.writerSheet().build();
                while (itor.hasNext()) {
                    List<T> list = itor.next();
                    if (ExtCollectionUtils.isEmpty(list)) {
                        continue;
                    }
                    if (isMap) {
                        // EasyExcel不支持Map直接导出，需要转换为List类型，作者真懒
                        // <https://github.com/alibaba/easyexcel/issues/1219>
                        // <https://github.com/alibaba/easyexcel/issues/582>
                        List<List<?>> mapDataContainer = list.stream()
                                .map(r -> {
                                    Map<?, ?> map = (Map<?, ?>)r;
                                    return leavesKeys.stream().map(map::get).collect(Collectors.toList());
                                })
                                .collect(Collectors.toList());
                        excelWriter.write(mapDataContainer, writeSheet);
                        continue;
                    }
                    excelWriter.write(list, writeSheet);
                }
            }
        } catch (Exception e) {
            throw BaseException.of(ResponseCode.B0001, "下载文件失败", e)
                    .appendDebugInfo(e.getMessage());
        }
    }

    /**
     * 表头项创建参考{@link AbstractHeaderCreator}
     *
     * @param rsp      响应
     * @param fileName 文件名称(enum枚举）
     * @param clazz    导出的目标类
     * @param items    表头项
     * @param data     完整数据
     */
    public static <T> void export(HttpServletResponse rsp, String fileName, Class<T> clazz,
                                  List<TreeItem> items, List<T> data) {
        export(rsp, fileName, clazz, items, Collections.singletonList(data).iterator());
    }

    public static String getFilename(TemporalAccessor startTime, TemporalAccessor endTime) {
        // 文件名保留年月日
        String sst = TimeUtils.format(startTime, TimeUtils.FMT_DATE);
        String set = TimeUtils.format(endTime, TimeUtils.FMT_DATE);
        return sst + "至" + set + "数据导出";
    }
}
