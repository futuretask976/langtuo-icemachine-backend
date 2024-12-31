package com.langtuo.teamachine.biz.excel;

import com.google.common.collect.Maps;
import com.langtuo.teamachine.biz.excel.handler.DefaultExcelHandler;
import com.langtuo.teamachine.biz.excel.handler.ExcelHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExcelHandlerFactory implements InitializingBean {
    /**
     * Excel 处理器映射，key 是 tenantCode，val 是处理器
     */
    private Map<String, ExcelHandler> excelHandlerMap;

    @Override
    public void afterPropertiesSet() {
        // 初始化部署导出映射
        if (excelHandlerMap == null) {
            synchronized (ExcelHandlerFactory.class) {
                if (excelHandlerMap == null) {
                    initExcelHandlerMap();
                }
            }
        }
    }

    public ExcelHandler getExcelHandler(String tenantCode) {
        return new DefaultExcelHandler();
    }

    public void initExcelHandlerMap() {
        excelHandlerMap = Maps.newHashMap();
        // TODO
    }
}
