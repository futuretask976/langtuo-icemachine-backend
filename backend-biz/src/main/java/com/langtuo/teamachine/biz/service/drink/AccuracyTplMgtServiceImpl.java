package com.langtuo.teamachine.biz.service.drink;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.AccuracyTplDTO;
import com.langtuo.teamachine.api.request.drink.AccuracyTplPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.drink.AccuracyTplMgtService;
import com.langtuo.teamachine.biz.aync.AsyncDispatcher;
import com.langtuo.teamachine.dao.accessor.drink.AccuracyTplAccessor;
import com.langtuo.teamachine.dao.accessor.drink.AccuracyTplToppingAccessor;
import com.langtuo.teamachine.dao.po.drink.AccuracyTplPO;
import com.langtuo.teamachine.dao.po.drink.AccuracyTplToppingPO;
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

import static com.langtuo.teamachine.biz.convertor.drink.AccuracyTplMgtConvertor.convertToAccuracyTplPO;
import static com.langtuo.teamachine.biz.convertor.drink.AccuracyTplMgtConvertor.convertToAccuracyTplToppingPO;

@Component
@Slf4j
public class AccuracyTplMgtServiceImpl implements AccuracyTplMgtService {
    @Resource
    private AccuracyTplAccessor accuracyTplAccessor;

    @Resource
    private AccuracyTplToppingAccessor accuracyTplToppingAccessor;

    @Resource
    private AsyncDispatcher asyncDispatcher;

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<AccuracyTplDTO>> list(String tenantCode) {
        try {
            List<AccuracyTplPO> poList = accuracyTplAccessor.list(tenantCode);
            List<AccuracyTplDTO> dtoList = convertToAccuracyTplPO(poList);

            // 根据 gmtModified 倒排
            if (!CollectionUtils.isEmpty(dtoList)) {
                dtoList.sort((o1, o2) -> o1.getGmtModified().equals(o2.getGmtModified()) ?
                        0 : o1.getGmtModified().before(o2.getGmtModified()) ? 1 : -1);
            }

            return TeaMachineResult.success(dtoList);
        } catch (Exception e) {
            log.error("list|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<AccuracyTplDTO>> search(String tenantName, String templateCode, String templateName,
            int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            PageInfo<AccuracyTplPO> pageInfo = accuracyTplAccessor.search(tenantName, templateCode, templateName,
                    pageNum, pageSize);
            List<AccuracyTplDTO> dtoList = convertToAccuracyTplPO(pageInfo.getList());

            return TeaMachineResult.success(new PageDTO<>(dtoList, pageInfo.getTotal(),
                    pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<AccuracyTplDTO> getByTplCode(String tenantCode, String specCode) {
        try {
            AccuracyTplPO po = accuracyTplAccessor.getByTplCode(tenantCode, specCode);
            AccuracyTplDTO dto = convertToAccuracyTplPO(po);

            return TeaMachineResult.success(dto);
        } catch (Exception e) {
            log.error("getByCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> put(AccuracyTplPutRequest request) {
        if (request == null || !request.isValid()) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        AccuracyTplPO po = convertToAccuracyTplPO(request);
        List<AccuracyTplToppingPO> toppingPOList = convertToAccuracyTplToppingPO(request);

        try {
            TeaMachineResult<Void> teaMachineResult;
            if (request.isPutNew()) {
                teaMachineResult = doPutNew(po, toppingPOList);
            } else {
                teaMachineResult = doPutUpdate(po, toppingPOList);
            }

            // 异步发送消息准备配置信息分发
            JSONObject jsonPayload = new JSONObject();
            jsonPayload.put(CommonConsts.JSON_KEY_BIZ_CODE, CommonConsts.BIZ_CODE_ACCURACY_TPL_UPDATED);
            jsonPayload.put(CommonConsts.JSON_KEY_TENANT_CODE, request.getTenantCode());
            jsonPayload.put(CommonConsts.JSON_KEY_TEMPLATE_CODE, request.getTemplateCode());
            asyncDispatcher.dispatch(jsonPayload);

            return teaMachineResult;
        } catch (Exception e) {
            log.error("put|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> deleteByTplCode(String tenantCode, String templateCode) {
        if (StringUtils.isEmpty(tenantCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            TeaMachineResult result = doDeleteByTplCode(tenantCode, templateCode);

            // 异步发送消息准备配置信息分发
            JSONObject jsonPayload = new JSONObject();
            jsonPayload.put(CommonConsts.JSON_KEY_BIZ_CODE, CommonConsts.BIZ_CODE_ACCURACY_TPL_DELETED);
            jsonPayload.put(CommonConsts.JSON_KEY_TENANT_CODE, tenantCode);
            jsonPayload.put(CommonConsts.JSON_KEY_TEMPLATE_CODE, templateCode);
            asyncDispatcher.dispatch(jsonPayload);

            return result;
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutNew(AccuracyTplPO po, List<AccuracyTplToppingPO> toppingPOList) {
        AccuracyTplPO exist = accuracyTplAccessor.getByTplCode(po.getTenantCode(), po.getTemplateCode());
        if (exist != null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
        }

        int inserted4AccuracyTpl = accuracyTplAccessor.insert(po);
        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted4AccuracyTpl) {
            log.error("putNewAccuracyTpl|error|" + inserted4AccuracyTpl);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }

        accuracyTplToppingAccessor.deleteByTplCode(po.getTenantCode(), po.getTemplateCode());
        int inserted4Topping = accuracyTplToppingAccessor.insertBatch(toppingPOList);
        if (inserted4Topping != toppingPOList.size()) {
            log.error("putUpdateAccuracyTplTopping|error|" + inserted4Topping);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutUpdate(AccuracyTplPO po, List<AccuracyTplToppingPO> toppingPOList) {
        AccuracyTplPO exist = accuracyTplAccessor.getByTplCode(po.getTenantCode(), po.getTemplateCode());
        if (exist == null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_NOT_FOUND));
        }

        int updated4AccuracyTpl = accuracyTplAccessor.update(po);
        if (CommonConsts.DB_UPDATED_ONE_ROW != updated4AccuracyTpl) {
            log.error("putUpdateAccuracyTpl|error|" + updated4AccuracyTpl);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }

        accuracyTplToppingAccessor.deleteByTplCode(po.getTenantCode(), po.getTemplateCode());
        int inserted4Topping = accuracyTplToppingAccessor.insertBatch(toppingPOList);
        if (inserted4Topping != toppingPOList.size()) {
            log.error("putUpdateAccuracyTplTopping|error|" + inserted4Topping);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doDeleteByTplCode(String tenantCode, String templateCode) {
        accuracyTplAccessor.deleteByTplCode(tenantCode, templateCode);
        return TeaMachineResult.success();
    }
}
