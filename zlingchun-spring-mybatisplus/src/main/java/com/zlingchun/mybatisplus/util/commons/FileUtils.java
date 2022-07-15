package com.zlingchun.mybatisplus.util.commons;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Set;

/**
 * @author achun
 * @create 2022/6/30
 * @description descrip
 */
public class FileUtils {

    public static void excelDownload(HttpServletResponse response, Collection<?> data, String fileName, Class clazz) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName , "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        // 这里需要设置不关闭流
        EasyExcel.write(response.getOutputStream(), clazz).autoCloseStream(Boolean.FALSE).sheet("模板")
                .doWrite(data);
    }

    public static void excelDownloadIncloudFields(HttpServletResponse response, Collection<?> data, String fileName, Class clazz, Set<String> includeColumnFiledNames) throws IOException{
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName , "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        // 这里需要设置不关闭流
        EasyExcel.write(response.getOutputStream(), clazz).includeColumnFiledNames(includeColumnFiledNames)
                .autoCloseStream(Boolean.FALSE).sheet("模板")
                .doWrite(data);
    }
}
