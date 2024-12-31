package com.langtuo.teamachine.biz.excel.parser;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface DeployParser {
    /**
     * 导出部署信息
     * @param tenantCode
     * @return
     */
    XSSFWorkbook export(String tenantCode);
}
