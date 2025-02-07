package com.saveslave.commons.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import com.saveslave.commons.config.EasyPoiConfig;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

/**
 * 
    * @ClassName: ExcelUtil
    * @Description: Excel工具类
    *
 */
public class ExcelUtil {
    
    private ExcelUtil() {
        throw new IllegalStateException("Utility class");
    }
    
    /**
     * Excel头部设置
     * @param response
     * @param fileName
     */
    public static void headerForXlsx(HttpServletResponse response, String fileName) {
        try {
            response.setContentType(MediaType.MICROSOFT_EXCEL.toString());
            response.setCharacterEncoding(CharEncoding.UTF_8);
            fileName = URLEncoder.encode(fileName, CharEncoding.UTF_8);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".xlsx");
        } catch (UnsupportedEncodingException e) {
        }
    }

    /**
     * 导出
     *
     * @param list           数据列表
     * @param title          标题
     * @param sheetName      sheet名称
     * @param pojoClass      元素类型
     * @param fileName       文件名
     * @param isCreateHeader 是否创建列头
     * @param response
     * @throws IOException
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName
            , boolean isCreateHeader, HttpServletResponse response) throws IOException {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);

    }

    /**
     * 导出
     *
     * @param list      数据列表
     * @param title     标题
     * @param sheetName sheet名称
     * @param pojoClass 元素类型
     * @param fileName  文件名
     * @param response
     * @throws IOException
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName
            , HttpServletResponse response) throws IOException {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }
    /**
     * 导出07Excel
     *
     * @param list      数据列表
     * @param title     标题
     * @param sheetName sheet名称
     * @param pojoClass 元素类型
     * @param fileName  文件名
     * @param response
     * @throws IOException
     */
    public static void export07Excel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName
            , HttpServletResponse response) throws IOException {
        if(!fileName.endsWith(".xlsx")){
            fileName = fileName + ".xlsx";
        }
        ExportParams exportParams = new ExportParams(title, sheetName,ExcelType.XSSF);
        if(list!=null&&list.size()>0){
            EasyPoiConfig easyPoiConfig = new EasyPoiConfig();
            List<String> needHandlerFields = new ArrayList<>();

            //导出Date类型自动转为yyyy-MM-dd HH:mm:ss
            Field[] declaredFields = pojoClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Class<?> type = declaredField.getType();
                if(type == Date.class){
                    Excel declaredAnnotation = declaredField.getDeclaredAnnotation(Excel.class);
                    if(declaredAnnotation != null&&(StringUtils.isBlank(declaredAnnotation.format()))&&StringUtils.isBlank(declaredAnnotation.exportFormat())){
                        String name = declaredAnnotation.name();
                        needHandlerFields.add(name);
                    }
                }

            }
            if(needHandlerFields.size()>0){
                easyPoiConfig.setNeedHandlerFields(needHandlerFields.toArray(new String[needHandlerFields.size()]));
                exportParams.setDataHandler(easyPoiConfig);
            }
        }

        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * 导出
     *
     * @param list     数据列表(元素是Map)
     * @param fileName 文件名
     * @param response
     * @throws IOException
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws IOException {
        defaultExport(list, fileName, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName
            , HttpServletResponse response, ExportParams exportParams) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.XSSF);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        workbook.write(response.getOutputStream());
    }

    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return Collections.emptyList();
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        return ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws Exception {
        if (file == null) {
            return Collections.emptyList();
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        return ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
    }
}
