package com.langtuo.teamachine.biz.excel.parser;

import com.langtuo.teamachine.api.request.drink.TeaPutRequest;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public interface TeaParser {
    /**
     * 导出茶品信息
     * @param tenantCode
     * @return
     */
    XSSFWorkbook export(String tenantCode);

    /**
     * 导出茶品信息
     * @param tenantCode
     * @return
     */
    List<TeaPutRequest> upload(String tenantCode, XSSFWorkbook workbook);
}
