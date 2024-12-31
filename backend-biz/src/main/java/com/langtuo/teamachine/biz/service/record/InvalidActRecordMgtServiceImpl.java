package com.langtuo.teamachine.biz.service.record;

import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.record.InvalidActRecordDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.record.InvalidActRecordMgtService;
import com.langtuo.teamachine.biz.manager.ShopGroupManager;
import com.langtuo.teamachine.dao.accessor.record.InvalidActRecordAccessor;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.langtuo.teamachine.biz.convertor.record.InvalidActRecordMgtConvertor.convertToInvalidActRecordDTO;

@Component
@Slf4j
public class InvalidActRecordMgtServiceImpl implements InvalidActRecordMgtService {
    @Resource
    private ShopGroupManager shopGroupManager;

    @Resource
    private InvalidActRecordAccessor invalidActRecordAccessor;

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<InvalidActRecordDTO> get(String tenantCode, String idempotentMark) {
        try {
            InvalidActRecordPO po = invalidActRecordAccessor.getByIdempotentMark(tenantCode, idempotentMark);
            InvalidActRecordDTO dto = convertToInvalidActRecordDTO(po, true);
            return TeaMachineResult.success(dto);
        } catch (Exception e) {
            log.error("get|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<InvalidActRecordDTO>> search(String tenantCode, String shopGroupCode,
            String shopCode, int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            PageInfo<InvalidActRecordPO> pageInfo = null;
            if (!StringUtils.isBlank(shopCode)) {
                pageInfo = invalidActRecordAccessor.searchByShopCodeList(tenantCode, Lists.newArrayList(shopCode),
                        pageNum, pageSize);
            } else if (!StringUtils.isBlank(shopGroupCode)) {
                pageInfo = invalidActRecordAccessor.searchByShopGroupCodeList(tenantCode, Lists.newArrayList(shopGroupCode),
                        pageNum, pageSize);
            } else {
                List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByLoginSession(tenantCode);
                pageInfo = invalidActRecordAccessor.searchByShopGroupCodeList(tenantCode, shopGroupCodeList,
                        pageNum, pageSize);
            }

            if (pageInfo == null) {
                return TeaMachineResult.success(new PageDTO<>(
                        null, 0, pageNum, pageSize));
            } else {
                return TeaMachineResult.success(new PageDTO<>(
                        convertToInvalidActRecordDTO(pageInfo.getList(), false),
                        pageInfo.getTotal(), pageNum, pageSize));
            }
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TeaMachineResult<Void> delete(String tenantCode, String warningRuleCode) {
        if (StringUtils.isEmpty(tenantCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            invalidActRecordAccessor.delete(tenantCode, warningRuleCode);
            return TeaMachineResult.success();
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }
}
