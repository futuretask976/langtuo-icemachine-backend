package com.langtuo.teamachine.biz.service.drink;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.TeaDTO;
import com.langtuo.teamachine.api.request.drink.TeaPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.drink.TeaMgtService;
import com.langtuo.teamachine.biz.excel.ExcelHandlerFactory;
import com.langtuo.teamachine.dao.accessor.drink.*;
import com.langtuo.teamachine.dao.accessor.menu.MenuDispatchCacheAccessor;
import com.langtuo.teamachine.dao.accessor.menu.SeriesTeaRelAccessor;
import com.langtuo.teamachine.dao.po.drink.*;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.langtuo.teamachine.biz.convertor.drink.TeaMgtConvertor.*;

@Component
@Slf4j
public class TeaMgtServiceImpl implements TeaMgtService {
    @Resource
    private TeaAccessor teaAccessor;

    @Resource
    private TeaUnitAccessor teaUnitAccessor;

    @Resource
    private SpecItemRuleAccessor specItemRuleAccessor;

    @Resource
    private ToppingAdjustRuleAccessor toppingAdjustRuleAccessor;

    @Resource
    private ToppingBaseRuleAccessor toppingBaseRuleAccessor;

    @Resource
    private SeriesTeaRelAccessor seriesTeaRelAccessor;

    @Resource
    private MenuDispatchCacheAccessor menuDispatchCacheAccessor;

    @Resource
    private ExcelHandlerFactory excelHandlerFactory;

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<TeaDTO> getByTeaCode(String tenantCode, String teaCode) {
        try {
            TeaPO po = teaAccessor.getByTeaCode(tenantCode, teaCode);
            TeaDTO dto = convertToTeaDTO(po, true);
            return TeaMachineResult.success(dto);
        } catch (Exception e) {
            log.error("getByCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<TeaDTO>> list(String tenantCode) {
        try {
            List<TeaPO> poList = teaAccessor.list(tenantCode);
            List<TeaDTO> dtoList = convertToTeaDTO(poList, false);

            return TeaMachineResult.success(dtoList);
        } catch (Exception e) {
            log.error("list|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<TeaDTO>> search(String tenantName, String teaCode, String teaName,
            int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            PageInfo<TeaPO> pageInfo = teaAccessor.search(tenantName, teaCode, teaName,
                    pageNum, pageSize);
            List<TeaDTO> dtoList = convertToTeaDTO(pageInfo.getList(), false);

            return TeaMachineResult.success(new PageDTO<>(dtoList, pageInfo.getTotal(),
                    pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> put(TeaPutRequest request) {
        if (request == null || !request.isValid()) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        TeaPO teaPO = convertToTeaPO(request);
        List<ToppingBaseRulePO> toppingBaseRulePOList = convertToToppingBaseRuleDTO(request);
        List<SpecItemRulePO> specItemRulePOList = convertToTeaSpecItemPO(request);
        List<TeaUnitPO> teaUnitPOList = convertToTeaUnitPO(request);
        List<ToppingAdjustRulePO> toppingAdjustRulePOList = convertToToppingAdjustRulePO(request);

        try {
            if (request.isPutImport()) {
                return doPutImport(teaPO, toppingBaseRulePOList, specItemRulePOList, teaUnitPOList,
                        toppingAdjustRulePOList);
            } else if (request.isPutNew()) {
                return doPutNew(teaPO, toppingBaseRulePOList, specItemRulePOList, teaUnitPOList,
                        toppingAdjustRulePOList);
            } else {
                return doPutUpdate(teaPO, toppingBaseRulePOList, specItemRulePOList, teaUnitPOList,
                        toppingAdjustRulePOList);
            }
        } catch (Exception e) {
            log.error("put|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutImport(TeaPO teaPO, List<ToppingBaseRulePO> toppingBaseRulePOList,
            List<SpecItemRulePO> specItemRulePOList, List<TeaUnitPO> teaUnitPOList,
            List<ToppingAdjustRulePO> toppingAdjustRulePOList) {
        int deleted4Tea = teaAccessor.deleteByTeaCode(teaPO.getTenantCode(), teaPO.getTeaCode());
        if (CommonConsts.DB_DELETED_ONE_ROW != deleted4Tea) {
            log.error("doPutImport|deleteTeaError|" + deleted4Tea);
        }

        int inserted4Tea = teaAccessor.insert(teaPO);
        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted4Tea) {
            log.error("doPutImport|insertTeaError|" + inserted4Tea);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }

        update4Tea(teaPO, toppingBaseRulePOList, specItemRulePOList, teaUnitPOList, toppingAdjustRulePOList);

        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutNew(TeaPO teaPO, List<ToppingBaseRulePO> toppingBaseRulePOList,
            List<SpecItemRulePO> specItemRulePOList, List<TeaUnitPO> teaUnitPOList,
            List<ToppingAdjustRulePO> toppingAdjustRulePOList) {
        TeaPO exist = teaAccessor.getByTeaCode(teaPO.getTenantCode(), teaPO.getTeaCode());
        if (exist != null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
        }

        int inserted4Tea = teaAccessor.insert(teaPO);
        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted4Tea) {
            log.error("putNewTea|error|" + inserted4Tea);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }

        update4Tea(teaPO, toppingBaseRulePOList, specItemRulePOList, teaUnitPOList, toppingAdjustRulePOList);

        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutUpdate(TeaPO teaPO, List<ToppingBaseRulePO> toppingBaseRulePOList,
            List<SpecItemRulePO> specItemRulePOList, List<TeaUnitPO> teaUnitPOList,
            List<ToppingAdjustRulePO> toppingAdjustRulePOList) {
        TeaPO exist = teaAccessor.getByTeaCode(teaPO.getTenantCode(), teaPO.getTeaCode());
        if (exist == null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_NOT_FOUND));
        }

        int updated4Tea = teaAccessor.update(teaPO);
        if (CommonConsts.DB_UPDATED_ONE_ROW != updated4Tea) {
            log.error("putUpdateTea|error|" + JSON.toJSONString(teaPO));
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }

        update4Tea(teaPO, toppingBaseRulePOList, specItemRulePOList, teaUnitPOList, toppingAdjustRulePOList);

        return TeaMachineResult.success();
    }

    private void update4Tea(TeaPO teaPO, List<ToppingBaseRulePO> toppingBaseRulePOList,
            List<SpecItemRulePO> specItemRulePOList, List<TeaUnitPO> teaUnitPOList,
            List<ToppingAdjustRulePO> toppingAdjustRulePOList) {
        toppingBaseRuleAccessor.deleteByTeaCode(teaPO.getTenantCode(), teaPO.getTeaCode());
        int inserted4ToppingBaseRule = toppingBaseRuleAccessor.insertBatch(toppingBaseRulePOList);
        if (inserted4ToppingBaseRule != toppingBaseRulePOList.size()) {
            log.error("updateToppingBaseRule|error|" + JSON.toJSONString(toppingBaseRulePOList));
        }

        specItemRuleAccessor.deleteByTeaCode(teaPO.getTenantCode(), teaPO.getTeaCode());
        int inserted4SpecItemRule = specItemRuleAccessor.insertBatch(specItemRulePOList);
        if (inserted4SpecItemRule != specItemRulePOList.size()) {
            log.error("updateTeaSpecItem|error|" + JSON.toJSONString(specItemRulePOList));
        }

        teaUnitAccessor.deleteByTeaCode(teaPO.getTenantCode(), teaPO.getTeaCode());
        int inserted4TeaUnit = teaUnitAccessor.insertBatch(teaUnitPOList);
        if (inserted4TeaUnit != teaUnitPOList.size()) {
            log.error("updateTeaUnit|error|" + JSON.toJSONString(teaUnitPOList));
        }

        toppingAdjustRuleAccessor.deleteByTeaCode(teaPO.getTenantCode(), teaPO.getTeaCode());
        int inserted4ToppingAdjustRule = toppingAdjustRuleAccessor.insertBatch(toppingAdjustRulePOList);
        if (inserted4ToppingAdjustRule != toppingAdjustRulePOList.size()) {
            log.error("updateToppingAdjustRule|error|" + JSON.toJSONString(toppingAdjustRulePOList));
        }

        // 删除菜单下发缓存
        menuDispatchCacheAccessor.clear(teaPO.getTenantCode());
    }

    @Override
    public TeaMachineResult<Void> deleteByTeaCode(String tenantCode, String teaCode) {
        if (StringUtils.isEmpty(tenantCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            return doDeleteByTeaCode(tenantCode, teaCode);
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doDeleteByTeaCode(String tenantCode, String teaCode) {
        int count = seriesTeaRelAccessor.countByTeaCode(tenantCode, teaCode);
        if (CommonConsts.DB_SELECT_ZERO_ROW != count) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(
                    ErrorCodeEnum.BIZ_ERR_CANNOT_DELETE_USING_OBJECT));
        }

        teaAccessor.deleteByTeaCode(tenantCode, teaCode);
        teaUnitAccessor.deleteByTeaCode(tenantCode, teaCode);
        toppingBaseRuleAccessor.deleteByTeaCode(tenantCode, teaCode);
        toppingAdjustRuleAccessor.deleteByTeaCode(tenantCode, teaCode);

        // 删除菜单下发缓存
        menuDispatchCacheAccessor.clear(tenantCode);

        return TeaMachineResult.success();
    }

    @Override
    public TeaMachineResult<XSSFWorkbook> exportByExcel(String tenantCode) {
        XSSFWorkbook xssfWorkbook = excelHandlerFactory.getExcelHandler(tenantCode)
                .getTeaHandler()
                .export(tenantCode);
        return TeaMachineResult.success(xssfWorkbook);
    }

    @Override
    public TeaMachineResult<Void> importByExcel(String tenantCode, XSSFWorkbook workbook) {
        if (StringUtils.isBlank(tenantCode) || workbook == null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        List<TeaPutRequest> teaPutRequestList = excelHandlerFactory.getExcelHandler(tenantCode)
                .getTeaHandler()
                .upload(tenantCode, workbook);

        for (TeaPutRequest teaPutRequest : teaPutRequestList) {
            long start4InsertSingle = System.currentTimeMillis();
            TeaMachineResult<Void> result = this.put(teaPutRequest);
            if (!result.isSuccess()) {
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
            }
            long end4InsertSingle = System.currentTimeMillis();
            log.debug("teaMgtServiceImpl|insertSingle|succ|" + (end4InsertSingle - start4InsertSingle));
        }
        return TeaMachineResult.success();
    }
}
