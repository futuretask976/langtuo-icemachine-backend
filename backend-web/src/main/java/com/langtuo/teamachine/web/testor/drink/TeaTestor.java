package com.langtuo.teamachine.web.testor.drink;//package com.langtuo.teamachine.biz.testor.drink;
//
//import com.google.common.collect.Lists;
//import com.langtuo.teamachine.api.request.drink.*;
//import com.langtuo.teamachine.dao.helper.SqlSessionFactoryHelper;
//import com.langtuo.teamachine.dao.mapper.drink.*;
//import com.langtuo.teamachine.dao.po.drink.*;
//import com.langtuo.teamachine.internal.constant.CommonConsts;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.*;
//import java.util.List;
//
//public class TeaTestor {
//    public static void main(String args[]) {
//        insert("tenant_001");
//    }
//
//    public static void insert(String tenantCode) {
//        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
//        SpecItemMapper specItemMapper = sqlSession.getMapper(SpecItemMapper.class);
//
//
//
//        File file = new File("/Users/Miya/Downloads/tea_export.xlsx");
//
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(file);
//
//            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//            Sheet sheet = workbook.getSheetAt(CommonConsts.SHEET_NUM_4_DATA);
//
//            List<TeaPutRequest> teaPutRequestList = Lists.newArrayList();
//            TeaPutRequest lastTeaPutRequest = null;
//            TeaUnitPutRequest lastTeaUnitPutRequest = null;
//            int rowIndex4Tea = CommonConsts.ROW_START_NUM_4_DATA;
//            while (true) {
//                Row row = sheet.getRow(rowIndex4Tea);
//                if (row == null) {
//                    break;
//                }
//
//                Cell cell4TeaCode = row.getCell(CommonConsts.TITLE_TEA_CODE_INDEX);
//                if (cell4TeaCode != null) {
//                    // 表示来到了新的 tea,开始新的茶品部分
//                    lastTeaPutRequest = new TeaPutRequest();
//                    lastTeaPutRequest.setTenantCode(tenantCode);
//                    teaPutRequestList.add(lastTeaPutRequest);
//
//                    // 设置茶品编码
//                    String teaCode = cell4TeaCode.getStringCellValue();
//                    lastTeaPutRequest.setTeaCode(teaCode);
//                    // 设置茶品名称
//                    Cell cell4TeaName = row.getCell(CommonConsts.TITLE_TEA_CODE_INDEX);
//                    String teaName = cell4TeaName.getStringCellValue();
//                    lastTeaPutRequest.setTeaName(teaName);
//                    // 设置外部茶品编码
//                    Cell cell4OuterTeaName = row.getCell(CommonConsts.TITLE_OUTER_TEA_CODE_INDEX);
//                    String outerTeaCode = cell4OuterTeaName.getStringCellValue();
//                    lastTeaPutRequest.setOuterTeaCode(outerTeaCode);
//                    // 设置状态
//                    Cell cell4State = row.getCell(CommonConsts.TITLE_STATE_INDEX);
//                    String state = cell4State.getStringCellValue();
//                    lastTeaPutRequest.setState(CommonConsts.STATE_DISABLED_LABEL.equals(state) ? CommonConsts.STATE_DISABLED : CommonConsts.STATE_ENABLED);
//                    // 设置类型编码
//                    Cell cell4TeaTypeCode = row.getCell(CommonConsts.TITLE_TEA_TYPE_CODE_INDEX);
//                    String teaTypeCode = cell4TeaTypeCode.getStringCellValue();
//                    lastTeaPutRequest.setTeaTypeCode(teaTypeCode);
//                    // 设置图片链接
//                    Cell cell4ImgLink = row.getCell(CommonConsts.TITLE_IMG_LINK_INDEX);
//                    String imgLink = cell4ImgLink.getStringCellValue();
//                    lastTeaPutRequest.setImgLink(imgLink);
//                }
//
//                Cell cell4TeaUnitName = row.getCell(CommonConsts.COL_START_NUM_4_TEA_UNIT);
//                if (cell4TeaUnitName != null) {
//                    // 表示来到了新的 teaUnit，开始新的 unit 部分
//                    lastTeaUnitPutRequest = new TeaUnitPutRequest();
//                    lastTeaPutRequest.addTeaUnit(lastTeaUnitPutRequest);
//
//                    // 设置 teaUnitName
//                    String teaUnitName = cell4TeaUnitName.getStringCellValue();
//                    lastTeaUnitPutRequest.setTeaUnitName(teaUnitName);
//                }
//
//                // 设置步骤序号
//                Cell cell4AdjustStepIndex = row.getCell(CommonConsts.TITLE_STEP_INDEX_INDEX);
//                if (cell4AdjustStepIndex == null) {
//                    break;
//                }
//                int adjustStepIndex = Double.valueOf(cell4AdjustStepIndex.getNumericCellValue()).intValue();
//                // 设置物料编码
//                Cell cell4AdjustToppingCode = row.getCell(CommonConsts.TITLE_TOPPING_CODE_INDEX);
//                String adjustToppingCode = cell4AdjustToppingCode.getStringCellValue();
//                // 设置调整用量
//                Cell cell4AdjustAmount = row.getCell(CommonConsts.TITLE_ACTUAL_AMOUNT_INDEX);
//                int actualAmount = Double.valueOf(cell4AdjustAmount.getNumericCellValue()).intValue();
//                // 每一行都是单独的 toppingAdjustRulePutRequest
//                ToppingAdjustRulePutRequest adjustRulePutRequest = new ToppingAdjustRulePutRequest();
//                adjustRulePutRequest.setBaseAmount(0);
//                adjustRulePutRequest.setAdjustType(CommonConsts.TOPPING_ADJUST_TYPE_INCRESE);
//                adjustRulePutRequest.setAdjustMode(CommonConsts.TOPPING_ADJUST_MODE_FIX);
//                adjustRulePutRequest.setStepIndex(adjustStepIndex);
//                adjustRulePutRequest.setToppingCode(adjustToppingCode);
//                adjustRulePutRequest.setAdjustAmount(actualAmount);
//                // 添加到 teaUnit 中
//                lastTeaUnitPutRequest.addToppingAdjustRulePutRequest(adjustRulePutRequest);
//
//                rowIndex4Tea++;
//            }
//            for (TeaPutRequest teaPutRequest : teaPutRequestList) {
//                // 需要构造 actStepList
//                List<TeaUnitPutRequest> teaUnitList = teaPutRequest.getTeaUnitList();
//                for (TeaUnitPutRequest teaUnitPutRequest : teaUnitList) {
//                    String teaUnitName = teaUnitPutRequest.getTeaUnitName();
//                    String[] teaUnitNameParts = teaUnitName.split("-");
//
//                    List<SpecItemPO> specItemPOList = Lists.newArrayList();
//                    for (String specItemName : teaUnitNameParts) {
//                        SpecItemPO specItemPO = specItemMapper.selectOne(tenantCode, null, specItemName);
//                        specItemPOList.add(specItemPO);
//                    }
//                    specItemPOList.sort((o1, o2) -> o1.getSpecCode().compareTo(o2.getSpecCode()));
//                    StringBuffer newTeaUnitCode = new StringBuffer();
//                    StringBuffer newTeaUnitName = new StringBuffer();
//                    List<SpecItemRulePutRequest> specItemRulePutRequestList = Lists.newArrayList();
//                    for (SpecItemPO specItemPO : specItemPOList) {
//                        if (newTeaUnitCode.length() > 0) {
//                            newTeaUnitCode.append("-");
//                        }
//                        newTeaUnitCode.append(specItemPO.getSpecItemCode());
//                        if (newTeaUnitName.length() > 0) {
//                            newTeaUnitName.append("-");
//                        }
//                        newTeaUnitName.append(specItemPO.getSpecItemName());
//
//                        SpecItemRulePutRequest specItemRulePutRequest = new SpecItemRulePutRequest();
//                        specItemRulePutRequest.setSpecCode(specItemPO.getSpecCode());
//                        specItemRulePutRequest.setSpecItemCode(specItemPO.getSpecItemCode());
//                        specItemRulePutRequestList.add(specItemRulePutRequest);
//                    }
//                    teaUnitPutRequest.setTeaUnitCode(newTeaUnitCode.toString());
//                    teaUnitPutRequest.setTeaUnitName(newTeaUnitName.toString());
//                    teaUnitPutRequest.setSpecItemRuleList(specItemRulePutRequestList);
//
//                    if (teaPutRequest.getActStepList() == null) {
//                        List<ToppingAdjustRulePutRequest> adjustRuleList = teaUnitPutRequest.getToppingAdjustRuleList();
//                        for (ToppingAdjustRulePutRequest adjustRule : adjustRuleList) {
//                            ToppingBaseRulePutRequest baseRulePutRequest = new ToppingBaseRulePutRequest();
//                            baseRulePutRequest.setToppingCode(adjustRule.getToppingCode());
//                            baseRulePutRequest.setBaseAmount(0);
//                            // 通过添加 toppingBaseRule，会自动构造 actStepList
//                            teaPutRequest.addToppingBaseRule(adjustRule.getStepIndex(), baseRulePutRequest);
//                        }
//                    }
//                }
//            }
//            System.out.println(teaPutRequestList);
//            for (TeaPutRequest teaPutRequest : teaPutRequestList) {
//                System.out.println(teaPutRequest.isValid());
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
