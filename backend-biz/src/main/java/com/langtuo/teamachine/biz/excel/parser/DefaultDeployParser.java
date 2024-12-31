package com.langtuo.teamachine.biz.excel.parser;

import com.langtuo.teamachine.dao.po.shop.ShopPO;
import com.langtuo.teamachine.dao.po.user.TenantPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DefaultDeployParser implements DeployParser {
    @Override
    public XSSFWorkbook export(String tenantCode) {
        // 创建一个新的工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表
        Sheet sheet = workbook.createSheet(LocaleUtils.getLang(CommonConsts.SHEET_NAME_4_DEPLOY_EXPORT));
        // 创建标题行（0基索引）
        Row row = sheet.createRow(CommonConsts.ROW_NUM_4_TITLE);
        // 创建单元格并设置值
        for (int i = 0; i < CommonConsts.TITLE_LIST_4_DEPLOY_EXPORT.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(LocaleUtils.getLang(CommonConsts.TITLE_LIST_4_DEPLOY_EXPORT.get(i)));
        }

        List<DeployPO> deployPOList = SpringAccessorUtils.getDeployAccessor().list(tenantCode);
        int lineIndex = CommonConsts.ROW_START_NUM_4_DEPLOY;
        for (DeployPO deployPO : deployPOList) {
            Row dataRow = sheet.createRow(lineIndex++);
            int columnIndex = CommonConsts.COL_START_NUM_4_DEPLOY;

            // 添加创建日期
            Cell cell4GmtCreated = dataRow.createCell(columnIndex++);
            cell4GmtCreated.setCellValue(transform(deployPO.getGmtCreated()));
            // 添加修改日期
            Cell cell4GmtModified = dataRow.createCell(columnIndex++);
            cell4GmtModified.setCellValue(transform(deployPO.getGmtModified()));
            // 添加商户编码
            Cell cell4TenantCode = dataRow.createCell(columnIndex++);
            cell4TenantCode.setCellValue(deployPO.getTenantCode());
            // 添加商户名称
            Cell cell4TenantName = dataRow.createCell(columnIndex++);
            TenantPO tenantPO = SpringAccessorUtils.getTenantAccessor().selectOneByTenantCode(tenantCode);
            if (tenantPO != null) {
                cell4TenantName.setCellValue(tenantPO.getTenantName());
            }
            // 添加部署编码
            Cell cell4DeployCode = dataRow.createCell(columnIndex++);
            cell4DeployCode.setCellValue(deployPO.getDeployCode());
            // 添加机器编码
            Cell cell4MachineCode = dataRow.createCell(columnIndex++);
            cell4MachineCode.setCellValue(deployPO.getMachineCode());
            // 添加型号编码
            Cell cell4ModelCode = dataRow.createCell(columnIndex++);
            cell4ModelCode.setCellValue(deployPO.getModelCode());
            // 添加店铺编码
            Cell cell4ShopCode = dataRow.createCell(columnIndex++);
            cell4ShopCode.setCellValue(deployPO.getShopCode());
            // 添加店铺名称
            Cell cell4ShopName = dataRow.createCell(columnIndex++);
            ShopPO shopPO = SpringAccessorUtils.getShopAccessor().getByShopCode(tenantCode, deployPO.getShopCode());
            if (shopPO != null) {
                cell4ShopName.setCellValue(shopPO.getShopName());
            }
            // 添加部署状态
            Cell cell4State = dataRow.createCell(columnIndex++);
            cell4State.setCellValue(deployPO.getState() == CommonConsts.DEPLOY_STATE_DISABLED ?
                    CommonConsts.DEPLOY_STATE_DISABLED_LABEL : CommonConsts.DEPLOY_STATE_ENABLED_LABEL);
        }
        return workbook;
    }

    public static String transform(Date date) {
        DateFormat format = new SimpleDateFormat(CommonConsts.DATE_FORMAT_FULL);
        return format.format(date);
    }
}
