package com.langtuo.teamachine.biz.service.rule;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.rule.DrainRuleDispatchDTO;
import com.langtuo.teamachine.api.model.rule.DrainRuleNewDTO;
import com.langtuo.teamachine.api.request.rule.DrainRuleDispatchNewPutRequest;
import com.langtuo.teamachine.api.request.rule.DrainRuleNewPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.rule.DrainRuleMgtService;
import com.langtuo.teamachine.biz.aync.AsyncDispatcher;
import com.langtuo.teamachine.biz.convertor.rule.DrainRuleMgtConvertor;
import com.langtuo.teamachine.biz.manager.AdminManager;
import com.langtuo.teamachine.biz.manager.ShopGroupManager;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.po.shop.ShopPO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.langtuo.teamachine.biz.convertor.rule.DrainRuleMgtConvertor.*;

@Component
@Slf4j
public class DrainRuleMgtServiceImpl implements DrainRuleMgtService {
    @Resource
    private AdminManager adminManager;

    @Resource
    private ShopGroupManager shopGroupManager;

    @Resource
    private ShopAccessor shopAccessor;

    @Resource
    private DrainRuleAccessor drainRuleAccessor;

    @Resource
    private DrainRuleExceptAccessor drainRuleExceptAccessor;

    @Resource
    private DrainRuleDispatchAccessor drainRuleDispatchAccessor;

    @Resource
    private AsyncDispatcher asyncDispatcher;

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<DrainRuleNewDTO> getByDrainRuleCode(String tenantCode, String drainRuleCode) {
        try {
            DrainRulePO po = drainRuleAccessor.getByDrainRuleCode(tenantCode, drainRuleCode);
            DrainRuleNewDTO dto = convertToDrainRuleDTO(po);

            return TeaMachineResult.success(dto);
        } catch (Exception e) {
            log.error("getByCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<DrainRuleNewDTO>> list(String tenantCode) {
        try {
            List<DrainRulePO> poList = drainRuleAccessor.list(tenantCode);
            List<DrainRuleNewDTO> dtoList = convertToDrainRuleDTO(poList);

            return TeaMachineResult.success(dtoList);
        } catch (Exception e) {
            log.error("list|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<DrainRuleNewDTO>> listByShopCode(String tenantCode, String shopCode) {
        try {
            ShopPO shopPO = shopAccessor.getByShopCode(tenantCode, shopCode);
            if (shopPO == null) {
                return TeaMachineResult.success();
            }

            List<DrainRuleDispatchPO> drainRuleDispatchPOList = drainRuleDispatchAccessor.listByShopGroupCode(
                    tenantCode, shopPO.getShopGroupCode());
            if (CollectionUtils.isEmpty(drainRuleDispatchPOList)) {
                return TeaMachineResult.success();
            }

            List<String> drainRuleCodeList = drainRuleDispatchPOList.stream()
                    .map(DrainRuleDispatchPO::getDrainRuleCode)
                    .collect(Collectors.toList());
            List<DrainRulePO> poList = drainRuleAccessor.listByDrainRuleCode(tenantCode,
                    drainRuleCodeList);
            List<DrainRuleNewDTO> dtoList = convertToDrainRuleDTO(poList);

            // 根据 gmtModified 倒排
            if (!CollectionUtils.isEmpty(dtoList)) {
                dtoList.sort((o1, o2) -> o1.getGmtModified().equals(o2.getGmtModified()) ?
                        0 : o1.getGmtModified().before(o2.getGmtModified()) ? 1 : -1);
            }

            return TeaMachineResult.success(dtoList);
        } catch (Exception e) {
            log.error("listByShopCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<DrainRuleNewDTO>> search(String tenantCode, String openRuleCode,
            String openRuleName, int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            PageInfo<DrainRulePO> pageInfo = drainRuleAccessor.search(tenantCode, openRuleCode,
                    openRuleName, pageNum, pageSize);
            List<DrainRuleNewDTO> dtoList = convertToDrainRuleDTO(pageInfo.getList());

            return TeaMachineResult.success(new PageDTO<>(dtoList, pageInfo.getTotal(), pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> put(DrainRuleNewPutRequest request) {
        if (request == null || !request.isValid()) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        DrainRulePO drainRulePO = convertToDrainRuleNewPO(request);
        List<DrainRuleExceptPO> drainRuleExceptPOList = convertToDrainRuleExceptNewPO(request);
        try {
            if (request.isPutNew()) {
                return doPutNew(drainRulePO, drainRuleExceptPOList);
            } else {
                return doPutUpdate(drainRulePO, drainRuleExceptPOList);
            }
        } catch (Exception e) {
            log.error("put|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> deleteByDrainRuleCode(String tenantCode, String drainRuleCode) {
        if (StringUtils.isEmpty(tenantCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            return doDeleteByDrainRuleCode(tenantCode, drainRuleCode);
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> putDispatch(DrainRuleDispatchNewPutRequest request) {
        if (request == null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        List<DrainRuleDispatchPO> poList = DrainRuleMgtConvertor.convertToDrainRuleDTO(request);
        try {
            TeaMachineResult<Void> result = doPutDispatch(request.getTenantCode(), request.getDrainRuleCode(), poList);

            // 异步发送消息准备配置信息分发
            JSONObject jsonPayload = getAsyncDispatchMsg(request.getTenantCode(), request.getDrainRuleCode());
            asyncDispatcher.dispatch(jsonPayload);

            return result;
        } catch (Exception e) {
            log.error("putDispatch|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<DrainRuleDispatchDTO> getDispatchByDrainRuleCode(String tenantCode, String drainRuleCode) {
        try {
            DrainRuleDispatchDTO dto = new DrainRuleDispatchDTO();
            dto.setDrainRuleCode(drainRuleCode);

            List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByLoginSession(tenantCode);
            List<DrainRuleDispatchPO> poList = drainRuleDispatchAccessor.listByDrainRuleCode(tenantCode,
                    drainRuleCode, shopGroupCodeList);
            if (!CollectionUtils.isEmpty(poList)) {
                dto.setShopGroupCodeList(poList.stream()
                        .map(DrainRuleDispatchPO::getShopGroupCode)
                        .collect(Collectors.toList()));
            }
            return TeaMachineResult.success(dto);
        } catch (Exception e) {
            log.error("getDispatchByDrainRuleCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TeaMachineResult<Void> doPutDispatch(String tenantCode, String drainRuleCode,
            List<DrainRuleDispatchPO> poList) {
        List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByLoginSession(tenantCode);
        drainRuleDispatchAccessor.deleteByDrainRuleCode(tenantCode, drainRuleCode, shopGroupCodeList);
        drainRuleDispatchAccessor.insertBatch(poList);

        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutNew(DrainRulePO po, List<DrainRuleExceptPO> drainRuleExceptPOList) {
        DrainRulePO exist = drainRuleAccessor.getByDrainRuleCode(po.getTenantCode(), po.getDrainRuleCode());
        if (exist != null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
        }

        int inserted = drainRuleAccessor.insert(po);
        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
            log.error("putNewDrainRule|error|" + inserted);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }

        drainRuleExceptAccessor.deleteByDrainRuleCode(po.getTenantCode(), po.getDrainRuleCode());
        int inserted4Except = drainRuleExceptAccessor.insertBatch(drainRuleExceptPOList);
        if (inserted4Except != drainRuleExceptPOList.size()) {
            log.error("putExceptTopping|error|" + inserted4Except);
        }
        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutUpdate(DrainRulePO po, List<DrainRuleExceptPO> drainRuleExceptPOList) {
        DrainRulePO exist = drainRuleAccessor.getByDrainRuleCode(po.getTenantCode(), po.getDrainRuleCode());
        if (exist == null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_NOT_FOUND));
        }

        int updated = drainRuleAccessor.update(po);
        if (CommonConsts.DB_UPDATED_ONE_ROW != updated) {
            log.error("putUpdateDrainRule|error|" + updated);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }

        drainRuleExceptAccessor.deleteByDrainRuleCode(po.getTenantCode(), po.getDrainRuleCode());
        int inserted4Except = drainRuleExceptAccessor.insertBatch(drainRuleExceptPOList);
        if (inserted4Except != drainRuleExceptPOList.size()) {
            log.error("putExceptTopping|error|" + inserted4Except);
        }

        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TeaMachineResult<Void> doDeleteByDrainRuleCode(String tenantCode, String drainRuleCode) {
        drainRuleAccessor.deleteByDrainRuleCode(tenantCode, drainRuleCode);
        drainRuleExceptAccessor.deleteByDrainRuleCode(tenantCode, drainRuleCode);
        drainRuleDispatchAccessor.deleteAllByDrainRuleCode(tenantCode, drainRuleCode);

        return TeaMachineResult.success();
    }

    private JSONObject getAsyncDispatchMsg(String tenantCode, String cleanRuleCode) {
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put(CommonConsts.JSON_KEY_BIZ_CODE, CommonConsts.BIZ_CODE_DRAIN_RULE_DISPATCH_NEW_REQUESTED);
        jsonPayload.put(CommonConsts.JSON_KEY_TENANT_CODE, tenantCode);
        jsonPayload.put(CommonConsts.JSON_KEY_LOGIN_NAME, adminManager.getAdminPOByLoginSession(tenantCode).getLoginName());
        jsonPayload.put(CommonConsts.JSON_KEY_DRAIN_RULE_CODE, cleanRuleCode);
        return jsonPayload;
    }
}
