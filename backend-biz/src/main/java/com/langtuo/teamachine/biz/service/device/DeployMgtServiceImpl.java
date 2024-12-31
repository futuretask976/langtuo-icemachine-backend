package com.langtuo.teamachine.biz.service.device;

import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.device.DeployDTO;
import com.langtuo.teamachine.api.request.device.DeployPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.device.DeployMgtService;
import com.langtuo.teamachine.biz.excel.ExcelHandlerFactory;
import com.langtuo.teamachine.biz.manager.ShopManager;
import com.langtuo.teamachine.biz.util.BizUtils;
import com.langtuo.teamachine.dao.accessor.device.DeployAccessor;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.accessor.user.TenantAccessor;
import com.langtuo.teamachine.dao.po.device.DeployPO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.langtuo.teamachine.biz.convertor.device.DeployMgtConvertor.convertToDeployDTO;

@Component
@Slf4j
public class DeployMgtServiceImpl implements DeployMgtService {
    @Resource
    private ShopManager shopManager;

    @Resource
    private DeployAccessor deployAccessor;

    @Resource
    private TenantAccessor tenantAccessor;

    @Resource
    private ShopAccessor shopAccessor;

    @Resource
    private ExcelHandlerFactory excelHandlerFactory;

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<DeployDTO>> search(String tenantCode, String deployCode, String shopCode,
            Integer state, int pageNum, int pageSize) {
        if (StringUtils.isBlank(tenantCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            List<String> shopCodeList = Lists.newArrayList();
            if (StringUtils.isBlank(shopCode)) {
                shopCodeList.addAll(shopManager.getShopCodeListByLoginSession(tenantCode));
            } else {
                shopCodeList.add(shopCode);
            }

            PageInfo<DeployPO> pageInfo = deployAccessor.search(tenantCode, deployCode, shopCodeList, state,
                    pageNum, pageSize);
            List<DeployDTO> dtoList = convertToDeployDTO(pageInfo.getList());

            return TeaMachineResult.success(new PageDTO<>(dtoList, pageInfo.getTotal(),
                    pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<DeployDTO> getByDeployCode(String tenantCode, String deployCode) {
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(deployCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            DeployPO po = deployAccessor.getByDeployCode(tenantCode, deployCode);
            DeployDTO dto = convertToDeployDTO(po);

            return TeaMachineResult.success(dto);
        } catch (Exception e) {
            log.error("getByCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<DeployDTO> getByMachineCode(String tenantCode, String machineCode) {
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(machineCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            DeployPO po = deployAccessor.getByMachineCode(tenantCode, machineCode);
            DeployDTO dto = convertToDeployDTO(po);
            
            return TeaMachineResult.success(dto);
        } catch (Exception e) {
            log.error("getByMachineCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> put(DeployPutRequest request) {
        if (request == null || !request.isValid()) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        DeployPO po = convertToDeployDTO(request);
        try {
            if (request.isPutNew()) {
                return doPutNew(po);
            } else {
                return doPutUpdate(po);
            }
        } catch (Exception e) {
            log.error("put|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> deleteByDeployCode(String tenantCode, String deployCode) {
        if (StringUtils.isEmpty(tenantCode) || StringUtils.isBlank(deployCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            return doDeleteByDeployCode(tenantCode, deployCode);
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Override
    public TeaMachineResult<String> generateDeployCode() {
        try {
            String deployCode = BizUtils.genRandomStr(6);
            return TeaMachineResult.success(deployCode);
        } catch (Exception e) {
            log.error("generateDeployCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Override
    public TeaMachineResult<String> generateMachineCode() {
        try {
            long machineCodeSeqVal = deployAccessor.getNextSeqVal4MachineCode();
            String machineCode = String.valueOf(machineCodeSeqVal);
            long needSupplyCnt = CommonConsts.DEPLOY_CODE_LENGTH - machineCode.length();
            if (machineCode.length() < CommonConsts.DEPLOY_CODE_LENGTH) {
                for (int i = 0; i < needSupplyCnt; i++) {
                    machineCode = CommonConsts.DEPLOY_CODE_COVERING_NUM + machineCode;
                }
            }
            // 获取当前日期
            Calendar calendar = Calendar.getInstance();
            // 创建一个SimpleDateFormat对象，指定目标格式
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConsts.DATE_FORMAT_SIMPLE);
            // 格式化日期
            String formattedDate = sdf.format(calendar.getTime());
            machineCode = formattedDate + machineCode;

            return TeaMachineResult.success(machineCode);
        } catch (Exception e) {
            log.error("generateMachineCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doDeleteByDeployCode(String tenantCode, String deployCode) {
        int deleted = deployAccessor.deleteByDeployCode(tenantCode, deployCode);
        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutNew(DeployPO po) {
        DeployPO exist = deployAccessor.getByDeployCode(po.getTenantCode(), po.getDeployCode());
        if (exist != null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
        }

        int inserted = deployAccessor.insert(po);
        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
            log.error("doPutNew|error|" + inserted);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutUpdate(DeployPO po) {
        DeployPO exist = deployAccessor.getByDeployCode(po.getTenantCode(), po.getDeployCode());
        if (exist == null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_NOT_FOUND));
        }

        int updated = deployAccessor.update(po);
        if (CommonConsts.DB_UPDATED_ONE_ROW != updated) {
            log.error("doPutUpdate|error|" + updated);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
        return TeaMachineResult.success();
    }

    @Override
    public TeaMachineResult<XSSFWorkbook> exportByExcel(String tenantCode) {
        XSSFWorkbook xssfWorkbook = excelHandlerFactory.getExcelHandler(tenantCode)
                .getDeployHandler()
                .export(tenantCode);
        return TeaMachineResult.success(xssfWorkbook);
    }
}
