package com.langtuo.teamachine.biz.service.record;

import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.record.CleanActRecordDTO;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.api.service.record.ActRecordMgtService;
import com.langtuo.teamachine.biz.manager.ShopGroupManager;
import com.langtuo.teamachine.dao.accessor.record.ActRecordAccessor;
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

import static com.langtuo.teamachine.biz.convertor.record.ActRecordMgtConvertor.convertToCleanActRecordDTO;

@Component
@Slf4j
public class ActRecordMgtServiceImpl implements ActRecordMgtService {
    @Resource
    private ShopGroupManager shopGroupManager;

    @Resource
    private ActRecordAccessor actRecordAccessor;

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<CleanActRecordDTO> get(String tenantCode, String idempotentMark) {
        try {
            CleanActRecordPO po = actRecordAccessor.getByIdempotentMark(tenantCode, idempotentMark);
            return IceMachineResult.success(convertToCleanActRecordDTO(po, true));
        } catch (Exception e) {
            log.error("get|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IceMachineResult<PageDTO<CleanActRecordDTO>> search(String tenantCode, String shopGroupCode,
                                                               String shopCode, int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            PageInfo<CleanActRecordPO> pageInfo = null;
            if (!StringUtils.isBlank(shopCode)) {
                pageInfo = actRecordAccessor.searchByShopCodeList(tenantCode, Lists.newArrayList(shopCode),
                        pageNum, pageSize);
            } else if (!StringUtils.isBlank(shopGroupCode)) {
                pageInfo = actRecordAccessor.searchByShopGroupCodeList(tenantCode, Lists.newArrayList(shopGroupCode),
                        pageNum, pageSize);
            } else {
                List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByLoginSession(tenantCode);
                pageInfo = actRecordAccessor.searchByShopGroupCodeList(tenantCode, shopGroupCodeList,
                        pageNum, pageSize);
            }

            if (pageInfo == null) {
                return IceMachineResult.success(new PageDTO<>(
                        null, 0, pageNum, pageSize));
            } else {
                return IceMachineResult.success(new PageDTO<>(
                        convertToCleanActRecordDTO(pageInfo.getList(), false),
                        pageInfo.getTotal(), pageNum, pageSize));
            }
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public IceMachineResult<Void> delete(String tenantCode, String idempotentMark) {
        if (StringUtils.isEmpty(tenantCode)) {
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }
        
        try {
            actRecordAccessor.delete(tenantCode, idempotentMark);
            return IceMachineResult.success();
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return IceMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }
}