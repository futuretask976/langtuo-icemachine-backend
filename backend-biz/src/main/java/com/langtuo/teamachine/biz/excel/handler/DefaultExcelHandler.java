package com.langtuo.teamachine.biz.excel.handler;

import com.langtuo.teamachine.biz.excel.parser.DefaultDeployParser;
import com.langtuo.teamachine.biz.excel.parser.DefaultTeaParser;
import com.langtuo.teamachine.biz.excel.parser.DeployParser;
import com.langtuo.teamachine.biz.excel.parser.TeaParser;

public class DefaultExcelHandler implements ExcelHandler {
    @Override
    public DeployParser getDeployHandler() {
        return new DefaultDeployParser();
    }

    @Override
    public TeaParser getTeaHandler() {
        return new DefaultTeaParser();
    }
}
