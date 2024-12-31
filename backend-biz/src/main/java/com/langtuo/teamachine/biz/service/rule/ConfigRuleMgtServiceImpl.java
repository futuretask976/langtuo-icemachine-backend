package com.langtuo.teamachine.biz.service.rule;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.service.rule.ConfigRuleMgtService;
import com.langtuo.teamachine.biz.aync.AsyncDispatcher;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.rule.CleanRuleDTO;
import com.langtuo.teamachine.api.model.rule.CleanRuleDispatchDTO;
import com.langtuo.teamachine.api.request.rule.CleanRuleDispatchPutRequest;
import com.langtuo.teamachine.api.request.rule.CleanRulePutRequest;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.biz.manager.AdminManager;
import com.langtuo.teamachine.biz.manager.ShopGroupManager;
import com.langtuo.teamachine.dao.accessor.rule.ConfigRuleAccessor;
import com.langtuo.teamachine.dao.accessor.rule.ConfigRuleDispatchAccessor;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.po.rule.ConfigRuleDispatchPO;
import com.langtuo.teamachine.dao.po.rule.ConfigRulePO;
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

import static com.langtuo.teamachine.biz.convertor.rule.ConfigRuleMgtConvertor.*;

@Component
@Slf4j
public class ConfigRuleMgtServiceImpl implements ConfigRuleMgtService {
    @Resource
    private AdminManager adminManager;

    @Resource
    private ShopGroupManager shopGroupManager;

    @Resource
    private ConfigRuleAccessor configRuleAccessor;

    @Resource
    private CleanRuleStepAccessor cleanRuleStepAccessor;

    @Resource
    private ConfigRuleDispatchAccessor configRuleDispatchAccessor;

    @Resource
    private CleanRuleExceptAccessor cleanRuleExceptAccessor;

    @Resource
    private ShopAccessor shopAccessor;

    @Resource
    private AsyncDispatcher asyncDispatcher;

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<CleanRuleDTO> getByCleanRuleCode(String tenantCode, String cleanRuleCode) {
        try {
            ConfigRulePO po = configRuleAccessor.getByCleanRuleCode(tenantCode, cleanRuleCode);
            CleanRuleDTO dto = convertToCleanRuleStepDTO(po);
            return IceMachineResult.success(dto);
        } catch (Exception e) {
            log.error("getByCode|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<List<CleanRuleDTO>> list(String tenantCode) {
        try {
            List<ConfigRulePO> poList = configRuleAccessor.selectList(tenantCode);
            List<CleanRuleDTO> dtoList = convertToCleanRuleDTO(poList);

            // 根据 gmtModified 倒排
            if (!CollectionUtils.isEmpty(dtoList)) {
                dtoList.sort((o1, o2) -> o1.getGmtModified().equals(o2.getGmtModified()) ?
                        0 : o1.getGmtModified().before(o2.getGmtModified()) ? 1 : -1);
            }

            return IceMachineResult.success(dtoList);
        } catch (Exception e) {
            log.error("list|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<List<CleanRuleDTO>> listByShopCode(String tenantCode, String shopCode) {
        try {
            ShopPO shopPO = shopAccessor.getByShopCode(tenantCode, shopCode);
            if (shopPO == null) {
                return IceMachineResult.success();
            }

            List<ConfigRuleDispatchPO> configRuleDispatchPOList = configRuleDispatchAccessor.listByShopGroupCode(
                    tenantCode, shopPO.getShopGroupCode());
            if (CollectionUtils.isEmpty(configRuleDispatchPOList)) {
                return IceMachineResult.success();
            }

            List<String> cleanRuleCodeList = configRuleDispatchPOList.stream()
                    .map(ConfigRuleDispatchPO::getConfigRuleCode)
                    .collect(Collectors.toList());
            List<ConfigRulePO> configRulePOList = configRuleAccessor.selectListByCleanRuleCode(tenantCode,
                    cleanRuleCodeList);
            List<CleanRuleDTO> cleanRuleDTOList = convertToCleanRuleDTO(configRulePOList);
            cleanRuleDTOList.sort((o1, o2) -> o1.getGmtModified().equals(o2.getGmtModified()) ?
                    0 : o1.getGmtModified().before(o2.getGmtModified()) ? 1 : -1);
            return IceMachineResult.success(cleanRuleDTOList);
        } catch (Exception e) {
            log.error("listByShopCode|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<PageDTO<CleanRuleDTO>> search(String tenantCode, String cleanRuleCode, String cleanRuleName,
                                                          int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            PageInfo<ConfigRulePO> pageInfo = configRuleAccessor.search(tenantCode, cleanRuleCode, cleanRuleName,
                    pageNum, pageSize);
            List<CleanRuleDTO> dtoList = convertToCleanRuleDTO(pageInfo.getList());
            return IceMachineResult.success(new PageDTO<>(dtoList, pageInfo.getTotal(),
                    pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public IceMachineResult<Void> put(CleanRulePutRequest request) {
        if (request == null || !request.isValid()) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        ConfigRulePO configRulePO = convertToCleanRulePO(request);
        List<CleanRuleStepPO> cleanRuleStepPOList = convertToCleanRuleStepPO(request);
        List<CleanRuleExceptPO> cleanRuleExceptPOList = convertToCleanRuleExceptPO(request);
        try {
            if (request.isPutNew()) {
                return doPutNew(configRulePO, cleanRuleStepPOList, cleanRuleExceptPOList);
            } else {
                return doPutUpdate(configRulePO, cleanRuleStepPOList, cleanRuleExceptPOList);
            }
        } catch (Exception e) {
            log.error("put|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
    }

    @Override
    public IceMachineResult<Void> deleteByCleanRuleCode(String tenantCode, String cleanRuleCode) {
        if (StringUtils.isEmpty(tenantCode)) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            return doDeleteByCleanRuleCode(tenantCode, cleanRuleCode);
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Override
    public IceMachineResult<Void> putDispatch(CleanRuleDispatchPutRequest request) {
        if (request == null) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        List<ConfigRuleDispatchPO> poList = convertToCleanRuleStepDTO(request);
        try {
            IceMachineResult<Void> result = doPutDispatch(request.getTenantCode(), request.getCleanRuleCode(), poList);

            // 异步发送消息准备配置信息分发
            JSONObject jsonPayload = getAsyncDispatchMsg(request.getTenantCode(), request.getCleanRuleCode());
            asyncDispatcher.dispatch(jsonPayload);

            return result;
        } catch (Exception e) {
            log.error("putDispatch|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<CleanRuleDispatchDTO> getDispatchByCleanRuleCode(String tenantCode, String cleanRuleCode) {
        try {
            CleanRuleDispatchDTO dto = new CleanRuleDispatchDTO();
            dto.setCleanRuleCode(cleanRuleCode);

            List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByLoginSession(tenantCode);
            List<ConfigRuleDispatchPO> poList = configRuleDispatchAccessor.listByCleanRuleCode(tenantCode, cleanRuleCode,
                    shopGroupCodeList);
            if (!CollectionUtils.isEmpty(poList)) {
                dto.setShopGroupCodeList(poList.stream()
                        .map(ConfigRuleDispatchPO::getMachineGroupCode)
                        .collect(Collectors.toList()));
            }
            return IceMachineResult.success(dto);
        } catch (Exception e) {
            log.error("getDispatchByCleanRuleCode|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private IceMachineResult<Void> doPutNew(ConfigRulePO po, List<CleanRuleStepPO> stepPOList,
                                            List<CleanRuleExceptPO> exceptPOList) {
        ConfigRulePO exist = configRuleAccessor.getByCleanRuleCode(po.getTenantCode(), po.getConfigRuleCode());
        if (exist != null) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
        }

        int inserted = configRuleAccessor.insert(po);
        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
            log.error("doPutNew|error|" + inserted);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }

        cleanRuleStepAccessor.deleteByCleanRuleCode(po.getTenantCode(), po.getConfigRuleCode());
        int inserted4Step = cleanRuleStepAccessor.insertBatch(stepPOList);
        if (inserted4Step != stepPOList.size()) {
            log.error("doPutNew|error|" + inserted4Step);
        }

        cleanRuleExceptAccessor.deleteByCleanRuleCode(po.getTenantCode(), po.getConfigRuleCode());
        if (!CollectionUtils.isEmpty(exceptPOList)) {
            int inserted4Except = cleanRuleExceptAccessor.insertBatch(exceptPOList);
            if (inserted4Except != exceptPOList.size()) {
                log.error("doPutNew|error|" + inserted4Except);
            }
        }
        return IceMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private IceMachineResult<Void> doPutUpdate(ConfigRulePO po, List<CleanRuleStepPO> stepPOList,
                                               List<CleanRuleExceptPO> exceptPOList) {
        ConfigRulePO exist = configRuleAccessor.getByCleanRuleCode(po.getTenantCode(), po.getConfigRuleCode());
        if (exist == null) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_NOT_FOUND));
        }

        int updated = configRuleAccessor.update(po);
        if (CommonConsts.DB_UPDATED_ONE_ROW != updated) {
            log.error("doPutUpdate|error|" + updated);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }

        cleanRuleStepAccessor.deleteByCleanRuleCode(po.getTenantCode(), po.getConfigRuleCode());
        int inserted4Step = cleanRuleStepAccessor.insertBatch(stepPOList);
        if (inserted4Step != stepPOList.size()) {
            log.error("putUpdateStep|error|" + inserted4Step);
        }

        cleanRuleExceptAccessor.deleteByCleanRuleCode(po.getTenantCode(), po.getConfigRuleCode());
        if (!CollectionUtils.isEmpty(exceptPOList)) {
            int inserted4Except = cleanRuleExceptAccessor.insertBatch(exceptPOList);
            if (inserted4Except != exceptPOList.size()) {
                log.error("doPutUpdate|error|" + inserted4Except);
            }
        }
        return IceMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private IceMachineResult<Void> doDeleteByCleanRuleCode(String tenantCode, String cleanRuleCode) {
        configRuleAccessor.deleteByCleanRuleCode(tenantCode, cleanRuleCode);
        cleanRuleStepAccessor.deleteByCleanRuleCode(tenantCode, cleanRuleCode);
        cleanRuleExceptAccessor.deleteByCleanRuleCode(tenantCode, cleanRuleCode);
        configRuleDispatchAccessor.deleteAllByCleanRuleCode(tenantCode, cleanRuleCode);

        return IceMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private IceMachineResult<Void> doPutDispatch(String tenantCode, String cleanRuleCode,
                                                 List<ConfigRuleDispatchPO> poList) {
        List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByLoginSession(tenantCode);
        configRuleDispatchAccessor.deleteByCleanRuleCode(tenantCode, cleanRuleCode, shopGroupCodeList);
        int inserted = configRuleDispatchAccessor.insertBatch(poList);
        if (inserted != poList.size()) {
            log.error("doPutDispatch|error|" + inserted);
        }

        return IceMachineResult.success();
    }

    private JSONObject getAsyncDispatchMsg(String tenantCode, String cleanRuleCode) {
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put(CommonConsts.JSON_KEY_BIZ_CODE, CommonConsts.BIZ_CODE_CLEAN_RULE_DISPATCH_REQUESTED);
        jsonPayload.put(CommonConsts.JSON_KEY_TENANT_CODE, tenantCode);
        jsonPayload.put(CommonConsts.JSON_KEY_LOGIN_NAME, adminManager.getAdminPOByLoginSession(tenantCode).getLoginName());
        jsonPayload.put(CommonConsts.JSON_KEY_CLEAN_RULE_CODE, cleanRuleCode);
        return jsonPayload;
    }
}
