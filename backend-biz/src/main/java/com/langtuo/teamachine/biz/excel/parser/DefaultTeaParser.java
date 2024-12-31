package com.langtuo.teamachine.biz.excel.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.langtuo.teamachine.api.request.drink.*;
import com.langtuo.teamachine.biz.excel.model.TeaExcel;
import com.langtuo.teamachine.biz.excel.model.TeaInfoPart;
import com.langtuo.teamachine.biz.excel.model.TeaUnitPart;
import com.langtuo.teamachine.biz.excel.model.ToppingAdjustRulePart;
import com.langtuo.teamachine.biz.util.ExcelUtils;
import com.langtuo.teamachine.dao.po.drink.*;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.langtuo.teamachine.biz.convertor.drink.TeaMgtConvertor.convertToSpecItemRulePutRequest;

/**
 * @author Jiaqing
 */
@Slf4j
public class DefaultTeaParser implements TeaParser {
    @Override
    public XSSFWorkbook export(String tenantCode) {
        // 获取数据
        List<TeaExcel> teaExcelList = fetchData4Export(tenantCode);

        // 创建一个新的工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表
        Sheet sheet = workbook.createSheet(CommonConsts.SHEET_NAME_4_TEA_EXPORT);
        // 创建标题行（0基索引）
        Row row = sheet.createRow(CommonConsts.ROW_NUM_4_TITLE);
        // 创建单元格并设置值
        for (int i = 0; i < CommonConsts.TITLE_LIST_4_TEA_EXPORT.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(LocaleUtils.getLang(CommonConsts.TITLE_LIST_4_TEA_EXPORT.get(i)));
        }

        int rowStartIndex4Tea = CommonConsts.ROW_START_NUM_4_TEA;
        for (TeaExcel teaExcel : teaExcelList) {
            TeaInfoPart teaInfoPart = teaExcel.getTeaInfoPart();
            int size4AdjustRuleList = teaExcel.getToppingAdjustRulePartList().size();

            Row dataRow = ExcelUtils.createRowIfAbsent(sheet, rowStartIndex4Tea);
            int columnNum = CommonConsts.COL_START_NUM_4_TEA_INFO;
            // 添加茶品编码
            Cell cell4TeaCode = dataRow.createCell(columnNum++);
            cell4TeaCode.setCellValue(teaInfoPart.getTeaCode());
            addMergedRegion4RowRange(sheet, cell4TeaCode, size4AdjustRuleList);
            // 添加茶品名称
            Cell cell4TeaName = dataRow.createCell(columnNum++);
            cell4TeaName.setCellValue(teaInfoPart.getTeaName());
            addMergedRegion4RowRange(sheet, cell4TeaName, size4AdjustRuleList);
            // 添加茶品状态
            Cell cell4State = dataRow.createCell(columnNum++);
            cell4State.setCellValue(teaInfoPart.getState() == CommonConsts.STATE_DISABLED
                    ? CommonConsts.STATE_DISABLED_LABEL : CommonConsts.STATE_ENABLED_LABEL);
            addMergedRegion4RowRange(sheet, cell4State, size4AdjustRuleList);
            // 添加茶品类型
            Cell cell4TeaType = dataRow.createCell(columnNum++);
            cell4TeaType.setCellValue(teaInfoPart.getTeaTypeCode());
            addMergedRegion4RowRange(sheet, cell4TeaType, size4AdjustRuleList);
            // 添加图片链接
            Cell cell4ImgLink = dataRow.createCell(columnNum++);
            cell4ImgLink.setCellValue(teaInfoPart.getImgLink());
            addMergedRegion4RowRange(sheet, cell4ImgLink, size4AdjustRuleList);

            List<TeaUnitPart> teaUnitPartList = teaExcel.getTeaUnitPartList();
            int rowRange4Unit = teaExcel.getToppingAdjustRulePartList().size() / teaExcel.getTeaUnitPartList().size();
            int rowIndex4Unit = rowStartIndex4Tea;
            for (TeaUnitPart teaUnitPart : teaUnitPartList) {
                Row row4TeaUnit = ExcelUtils.createRowIfAbsent(sheet, rowIndex4Unit);
                int columnIndex4TeaUnit = CommonConsts.COL_START_NUM_4_TEA_UNIT;
                // 添加unit名称
                Cell cell4UnitName = row4TeaUnit.createCell(columnIndex4TeaUnit++);
                cell4UnitName.setCellValue(teaUnitPart.getTeaUnitName());
                addMergedRegion4RowRange(sheet, cell4UnitName, rowRange4Unit);
                rowIndex4Unit = rowIndex4Unit + rowRange4Unit;
            }

            List<ToppingAdjustRulePart> toppingAdjustRulePartList = teaExcel.getToppingAdjustRulePartList();
            int rowIndex4AdjustRule = rowStartIndex4Tea;
            for (ToppingAdjustRulePart toppingAdjustRulePart : toppingAdjustRulePartList) {
                Row row4AdjustRule = ExcelUtils.createRowIfAbsent(sheet, rowIndex4AdjustRule++);
                int columnIndex4AdjustRule = CommonConsts.COL_START_NUM_4_ADJUST_RULE;
                // 添加步骤序号
                Cell cell4StepIndex = row4AdjustRule.createCell(columnIndex4AdjustRule++);
                cell4StepIndex.setCellValue(toppingAdjustRulePart.getStepIndex());
                // 添加物料编码
                Cell cell4ToppingCode = row4AdjustRule.createCell(columnIndex4AdjustRule++);
                cell4ToppingCode.setCellValue(toppingAdjustRulePart.getToppingCode());
                // 添加物料名称
                Cell cell4ToppingName = row4AdjustRule.createCell(columnIndex4AdjustRule++);
                cell4ToppingName.setCellValue(toppingAdjustRulePart.getToppingName());
                // 添加实际用量
                Cell cell4ActualAmount = row4AdjustRule.createCell(columnIndex4AdjustRule++);
                cell4ActualAmount.setCellValue(toppingAdjustRulePart.getActualAmount());
            }

            rowStartIndex4Tea = rowStartIndex4Tea + size4AdjustRuleList;
        }
        return workbook;
    }

    @Override
    public List<TeaPutRequest> upload(String tenantCode, XSSFWorkbook workbook) {
        Sheet sheet = workbook.getSheetAt(CommonConsts.SHEET_NUM_4_DATA);

        List<TeaPutRequest> teaPutRequestList = Lists.newArrayList();
        TeaPutRequest lastTeaPutRequest = null;
        TeaUnitPutRequest lastTeaUnitPutRequest = null;
        int rowIndex4Tea = CommonConsts.ROW_START_NUM_4_DATA;

        long start4Parse = System.currentTimeMillis();
        while (true) {
            Row row = sheet.getRow(rowIndex4Tea);
            if (row == null) {
                break;
            }

            Cell cell4TeaCode = row.getCell(CommonConsts.TITLE_TEA_CODE_INDEX);
            if (cell4TeaCode != null) {
                // 表示来到了新的 tea,开始新的茶品部分
                lastTeaPutRequest = new TeaPutRequest();
                lastTeaPutRequest.setPutImport(true);
                lastTeaPutRequest.setTenantCode(tenantCode);
                teaPutRequestList.add(lastTeaPutRequest);

                // 设置茶品编码
                String teaCode = cell4TeaCode.getStringCellValue();
                lastTeaPutRequest.setTeaCode(teaCode);
                // 设置茶品名称
                Cell cell4TeaName = row.getCell(CommonConsts.TITLE_TEA_NAME_INDEX);
                String teaName = cell4TeaName.getStringCellValue();
                lastTeaPutRequest.setTeaName(teaName);
                // 设置状态
                Cell cell4State = row.getCell(CommonConsts.TITLE_STATE_INDEX);
                String state = cell4State.getStringCellValue();
                lastTeaPutRequest.setState(CommonConsts.STATE_DISABLED_LABEL.equals(state) ? CommonConsts.STATE_DISABLED : CommonConsts.STATE_ENABLED);
                // 设置类型编码
                Cell cell4TeaTypeCode = row.getCell(CommonConsts.TITLE_TEA_TYPE_CODE_INDEX);
                String teaTypeCode = cell4TeaTypeCode.getStringCellValue();
                lastTeaPutRequest.setTeaTypeCode(teaTypeCode);
                // 设置图片链接
                Cell cell4ImgLink = row.getCell(CommonConsts.TITLE_IMG_LINK_INDEX);
                String imgLink = cell4ImgLink.getStringCellValue();
                lastTeaPutRequest.setImgLink(imgLink);
            }

            Cell cell4TeaUnitName = row.getCell(CommonConsts.COL_START_NUM_4_TEA_UNIT);
            if (cell4TeaUnitName != null) {
                // 表示来到了新的 teaUnit，开始新的 unit 部分
                lastTeaUnitPutRequest = new TeaUnitPutRequest();
                lastTeaPutRequest.addTeaUnit(lastTeaUnitPutRequest);

                // 设置 teaUnitName
                String teaUnitName = cell4TeaUnitName.getStringCellValue();
                lastTeaUnitPutRequest.setTeaUnitName(getTeaUnitName(tenantCode, teaUnitName));
                lastTeaUnitPutRequest.setTeaUnitCode(getTeaUnitCode(tenantCode, teaUnitName));
            }

            // 设置步骤序号
            Cell cell4AdjustStepIndex = row.getCell(CommonConsts.TITLE_STEP_INDEX_INDEX);
            if (cell4AdjustStepIndex == null) {
                break;
            }
            int adjustStepIndex = Double.valueOf(cell4AdjustStepIndex.getNumericCellValue()).intValue();
            // 设置物料编码
            Cell cell4AdjustToppingCode = row.getCell(CommonConsts.TITLE_TOPPING_CODE_INDEX);
            String adjustToppingCode = cell4AdjustToppingCode.getStringCellValue();
            // 设置调整用量
            Cell cell4AdjustAmount = row.getCell(CommonConsts.TITLE_ACTUAL_AMOUNT_INDEX);
            int actualAmount = Double.valueOf(cell4AdjustAmount.getNumericCellValue()).intValue();
            // 每一行都是单独的 toppingAdjustRulePutRequest
            ToppingAdjustRulePutRequest adjustRulePutRequest = new ToppingAdjustRulePutRequest();
            adjustRulePutRequest.setBaseAmount(0);
            adjustRulePutRequest.setAdjustType(CommonConsts.TOPPING_ADJUST_TYPE_INCRESE);
            adjustRulePutRequest.setAdjustMode(CommonConsts.TOPPING_ADJUST_MODE_FIX);
            adjustRulePutRequest.setStepIndex(adjustStepIndex);
            adjustRulePutRequest.setToppingCode(adjustToppingCode);
            adjustRulePutRequest.setAdjustAmount(actualAmount);
            // 添加到 teaUnit 中
            lastTeaUnitPutRequest.addToppingAdjustRulePutRequest(adjustRulePutRequest);

            rowIndex4Tea++;
        }
        long end4Parse = System.currentTimeMillis();
        log.debug("parseExcel|succ|consumeTime=" + (end4Parse - start4Parse));

        long start4Construct = System.currentTimeMillis();
        Map<String, SpecItemPO> specItemPOMapByName = getSpecItemPOMap(tenantCode);
        for (TeaPutRequest teaPutRequest : teaPutRequestList) {
            // 需要构造 actStepList
            List<TeaUnitPutRequest> teaUnitList = teaPutRequest.getTeaUnitList();
            teaPutRequest.setToppingBaseRuleList(getToppingBaseRulePutRequest(teaUnitList));
            teaPutRequest.setSpecRuleList(getSpecRulePutRequestList(tenantCode, teaUnitList,
                    specItemPOMapByName));
        }
        long end4Construct = System.currentTimeMillis();
        log.debug("constructRequest|succ|consumeTime=" + (end4Construct - start4Construct));
        return teaPutRequestList;
    }

    private List<SpecRulePutRequest> getSpecRulePutRequestList(String tenantCode,
            List<TeaUnitPutRequest> teaUnitPutRequestList, Map<String, SpecItemPO> specItemPOMapByName) {
        if (CollectionUtils.isEmpty(teaUnitPutRequestList)) {
            return null;
        }

        Map<String, SpecItemPO> specItemPOMap = Maps.newHashMap();
        for (TeaUnitPutRequest teaUnitPutRequest : teaUnitPutRequestList) {
            String teaUnitName = teaUnitPutRequest.getTeaUnitName();
            String[] teaUnitNameParts = teaUnitName.split(CommonConsts.HORIZONTAL_BAR);

            for (String specItemName : teaUnitNameParts) {
                SpecItemPO specItemPO = specItemPOMapByName.get(specItemName);
                specItemPOMap.putIfAbsent(specItemPO.getSpecItemCode(), specItemPO);
            }
        }

        Map<String, SpecRulePutRequest> specRulePutRequestMap = Maps.newHashMap();
        for (Map.Entry<String, SpecItemPO> entry : specItemPOMap.entrySet()) {
            SpecItemPO specItemPO = entry.getValue();
            SpecRulePutRequest specRulePutRequest = specRulePutRequestMap.get(specItemPO.getSpecCode());
            if (specRulePutRequest == null) {
                specRulePutRequest = new SpecRulePutRequest();
                specRulePutRequest.setSpecCode(specItemPO.getSpecCode());
                specRulePutRequestMap.put(specRulePutRequest.getSpecCode(), specRulePutRequest);
            }
            specRulePutRequest.addSpecItemRulePutRequest(convertToSpecItemRulePutRequest(specItemPO));
        }

        List<SpecRulePutRequest> list = Lists.newArrayList();
        for (Map.Entry<String, SpecRulePutRequest> entry : specRulePutRequestMap.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    private List<ToppingBaseRulePutRequest> getToppingBaseRulePutRequest(
            List<TeaUnitPutRequest> teaUnitPutRequestList) {
        if (CollectionUtils.isEmpty(teaUnitPutRequestList)) {
            return null;
        }

        TeaUnitPutRequest teaUnitPutRequest = teaUnitPutRequestList.get(CommonConsts.LIST_INDEX_FIRST);
        List<ToppingAdjustRulePutRequest> toppingAdjustRulePutRequestList = teaUnitPutRequest.getToppingAdjustRuleList();

        List<ToppingBaseRulePutRequest> toppingBaseRulePutRequestList = Lists.newArrayList();
        for (ToppingAdjustRulePutRequest toppingAdjustRulePutRequest : toppingAdjustRulePutRequestList) {
            ToppingBaseRulePutRequest toppingBaseRulePutRequest = new ToppingBaseRulePutRequest();
            toppingBaseRulePutRequest.setStepIndex(toppingAdjustRulePutRequest.getStepIndex());
            toppingBaseRulePutRequest.setToppingCode(toppingAdjustRulePutRequest.getToppingCode());
            toppingBaseRulePutRequest.setBaseAmount(CommonConsts.AMOUNT_ZERO);
            toppingBaseRulePutRequestList.add(toppingBaseRulePutRequest);
        }
        return toppingBaseRulePutRequestList;
    }

    private void addMergedRegion4RowRange(Sheet sheet, Cell cell, int rowRange) {
        if (rowRange == 1) {
            return;
        }
        ExcelUtils.addMergedRegion(sheet, cell.getRowIndex(), cell.getRowIndex() + rowRange - CommonConsts.NUM_ONE,
                cell.getColumnIndex(), cell.getColumnIndex());
    }

    private List<TeaExcel> fetchData4Export(String tenantCode) {
        List<TeaExcel> teaExcelList = Lists.newArrayList();
        List<TeaPO> teaPOList = SpringAccessorUtils.getTeaAccessor().list(tenantCode);
        for (TeaPO teaPO : teaPOList) {
            TeaExcel teaExcel = new TeaExcel();
            teaExcel.setTeaInfoPart(convertToTeaInfoExcel(teaPO));
            teaExcelList.add(teaExcel);

            List<ToppingBaseRulePO> toppingBaseRulePOList = SpringAccessorUtils.getToppingBaseRuleAccessor()
                    .listByTeaCode(teaPO.getTenantCode(), teaPO.getTeaCode());
            Map<Integer, Map<String, ToppingBaseRulePO>> baseRuleMapByStepIndex = convertToBaseRuleMap(
                    toppingBaseRulePOList);

            List<TeaUnitPart> teaUnitPartList = convertToTeaUnitPart(SpringAccessorUtils.getTeaUnitAccessor()
                    .listByTeaCode(teaPO.getTenantCode(), teaPO.getTeaCode()));
            teaExcel.addTeaUnit(teaUnitPartList);

            for (TeaUnitPart teaUnitPart : teaUnitPartList) {
                List<ToppingAdjustRulePO> toppingAdjustRulePOList = SpringAccessorUtils.getToppingAdjustRuleAccessor()
                        .listByTeaUnitCode(teaPO.getTenantCode(), teaPO.getTeaCode(),
                                teaUnitPart.getTeaUnitCode());
                teaExcel.addAdjustRulePart(convertToAdjustRulePart(baseRuleMapByStepIndex, toppingAdjustRulePOList));
            }
        }
        return teaExcelList;
    }

    private List<ToppingAdjustRulePart> convertToAdjustRulePart(
            Map<Integer, Map<String, ToppingBaseRulePO>> baseRuleMapByStepIndex,
            List<ToppingAdjustRulePO> adjustRulePOList) {
        List<ToppingAdjustRulePart> resultList = Lists.newArrayList();
        for (ToppingAdjustRulePO adjustRulePO : adjustRulePOList) {
            ToppingAdjustRulePart adjustRulePart = new ToppingAdjustRulePart();
            adjustRulePart.setStepIndex(adjustRulePO.getStepIndex());

            ToppingPO toppingPO = SpringAccessorUtils.getToppingAccessor().getByToppingCode(
                    adjustRulePO.getTenantCode(), adjustRulePO.getToppingCode());
            if (toppingPO != null) {
                adjustRulePart.setToppingCode(toppingPO.getToppingCode());
                adjustRulePart.setToppingName(toppingPO.getToppingName());
            }

            Map<String, ToppingBaseRulePO> baseRuleMapByToppingCode = baseRuleMapByStepIndex.get(
                    adjustRulePO.getStepIndex());
            ToppingBaseRulePO baseRulePO = baseRuleMapByToppingCode.get(adjustRulePO.getToppingCode());
            int baseAmount = baseRulePO.getBaseAmount();
            int adjustAmount = adjustRulePO.getAdjustAmount();
            int adjustType = adjustRulePO.getAdjustType();
            int adjustMode = adjustRulePO.getAdjustMode();
            int actualAmount = 0;
            if (CommonConsts.TOPPING_ADJUST_TYPE_REDUCE == adjustType) {
                if (CommonConsts.TOPPING_ADJUST_MODE_FIX == adjustMode) {
                    actualAmount = baseAmount - adjustAmount;
                } else {
                    actualAmount = baseAmount * (CommonConsts.NUM_ONE - adjustAmount);
                }
            } else {
                if (CommonConsts.TOPPING_ADJUST_MODE_FIX == adjustMode) {
                    actualAmount = baseAmount + adjustAmount;
                } else {
                    actualAmount = baseAmount + (CommonConsts.NUM_ONE - adjustAmount);
                }
            }
            adjustRulePart.setActualAmount(actualAmount < CommonConsts.AMOUNT_ZERO ? CommonConsts.AMOUNT_ZERO : actualAmount);

            resultList.add(adjustRulePart);
        }
        return resultList;
    }

    private Map<Integer, Map<String, ToppingBaseRulePO>> convertToBaseRuleMap(List<ToppingBaseRulePO> poList) {
        if (poList == null) {
            return null;
        }

        Map<Integer, Map<String, ToppingBaseRulePO>> mapByStepIndex = Maps.newHashMap();
        for (ToppingBaseRulePO po : poList) {
            int stepIndex = po.getStepIndex();
            String toppingCode = po.getToppingCode();

            Map<String, ToppingBaseRulePO> mapByToppingCode = mapByStepIndex.get(stepIndex);
            if (mapByToppingCode == null) {
                mapByToppingCode = Maps.newHashMap();
                mapByStepIndex.put(stepIndex, mapByToppingCode);
            }
            mapByToppingCode.put(toppingCode, po);
        }
        return mapByStepIndex;
    }

    private static List<TeaUnitPart> convertToTeaUnitPart(List<TeaUnitPO> teaUnitPOList) {
        Set<TeaUnitPart> result = Sets.newHashSet();
        for (TeaUnitPO teaUnitPO : teaUnitPOList) {
            TeaUnitPart teaUnitPart = new TeaUnitPart();
            teaUnitPart.setTeaUnitName(teaUnitPO.getTeaUnitName());
            teaUnitPart.setTeaUnitCode(teaUnitPO.getTeaUnitCode());
            result.add(teaUnitPart);
        }
        return result.stream().collect(Collectors.toList());
    }

    private TeaInfoPart convertToTeaInfoExcel(TeaPO teaPO) {
        TeaInfoPart teaInfoPart = new TeaInfoPart();
        teaInfoPart.setTeaCode(teaPO.getTeaCode());
        teaInfoPart.setTeaName(teaPO.getTeaName());
        teaInfoPart.setTeaTypeCode(teaPO.getTeaTypeCode());
        teaInfoPart.setImgLink(teaPO.getImgLink());
        teaInfoPart.setState(teaPO.getState());
        return teaInfoPart;
    }

    private String getTeaUnitName(String tenantCode, String inputTeaUnitName) {
        if (StringUtils.isBlank(inputTeaUnitName)) {
            return null;
        }

        String[] specItemNameArr = inputTeaUnitName.split(CommonConsts.HORIZONTAL_BAR);
        List<String> specItemNameList = Lists.newArrayList();
        for (String specItemName : specItemNameArr) {
            specItemNameList.add(specItemName);
        }
        specItemNameList.sort((o1, o2) -> o2.compareTo(o1));
        return String.join(CommonConsts.HORIZONTAL_BAR, specItemNameList);
    }

    private String getTeaUnitCode(String tenantCode, String inputTeaUnitName) {
        if (StringUtils.isBlank(inputTeaUnitName)) {
            return null;
        }

        String[] specItemNameArr = inputTeaUnitName.split(CommonConsts.HORIZONTAL_BAR);
        List<String> specItemCodeList = Lists.newArrayList();
        for (String specItemName : specItemNameArr) {
            SpecItemPO specItemPO = SpringAccessorUtils.getSpecItemAccessor().getBySpecItemName(tenantCode,
                    specItemName);
            if (specItemPO != null) {
                specItemCodeList.add(specItemPO.getSpecItemCode());
            }
        }
        specItemCodeList.sort((o1, o2) -> o2.compareTo(o1));
        return String.join(CommonConsts.HORIZONTAL_BAR, specItemCodeList);
    }

    private Map<String, SpecItemPO> getSpecItemPOMap(String tenantCode) {
        Map<String, SpecItemPO> map = Maps.newHashMap();
        List<SpecItemPO> poList = SpringAccessorUtils.getSpecItemAccessor().list(tenantCode);
        for (SpecItemPO po : poList) {
            map.put(po.getSpecItemName(), po);
        }
        return map;
    }
}
