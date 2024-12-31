package com.langtuo.teamachine.biz.service.drink;

import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.ToppingDTO;
import com.langtuo.teamachine.api.request.drink.ToppingPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.drink.ToppingMgtService;
import com.langtuo.teamachine.dao.accessor.drink.ToppingAccessor;
import com.langtuo.teamachine.dao.accessor.menu.MenuDispatchCacheAccessor;
import com.langtuo.teamachine.dao.mapper.drink.ToppingBaseRuleMapper;
import com.langtuo.teamachine.dao.po.drink.ToppingPO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.langtuo.teamachine.biz.convertor.drink.ToppingMgtConvertor.convertToToppingDTO;

@Component
@Slf4j
public class ToppingMgtServiceImpl implements ToppingMgtService {
    @Resource
    private ToppingAccessor toppingAccessor;

    @Resource
    private ToppingBaseRuleMapper toppingBaseRuleMapper;

    @Resource
    private MenuDispatchCacheAccessor menuDispatchCacheAccessor;

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<ToppingDTO>> list(String tenantCode) {
        TeaMachineResult<List<ToppingDTO>> teaMachineResult;
        try {
            List<ToppingPO> poList = toppingAccessor.list(tenantCode);
            List<ToppingDTO> dtoList = convertToToppingDTO(poList);
            
            teaMachineResult = TeaMachineResult.success(dtoList);
        } catch (Exception e) {
            log.error("list|fatal|e=" + e.getMessage(), e);
            teaMachineResult = TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
        return teaMachineResult;
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<ToppingDTO>> search(String tenantName, String toppingTypeCode,
            String toppingTypeName, int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            PageInfo<ToppingPO> pageInfo = toppingAccessor.search(tenantName, toppingTypeCode, toppingTypeName,
                    pageNum, pageSize);
            List<ToppingDTO> dtoList = convertToToppingDTO(pageInfo.getList());

            return TeaMachineResult.success(new PageDTO<>(dtoList, pageInfo.getTotal(),
                    pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<ToppingDTO> getByToppingCode(String tenantCode, String toppingTypeCode) {
        try {
            ToppingPO po = toppingAccessor.getByToppingCode(tenantCode, toppingTypeCode);
            ToppingDTO dto = convertToToppingDTO(po);

            return TeaMachineResult.success(dto);
        } catch (Exception e) {
            log.error("getByCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> put(ToppingPutRequest request) {
        if (request == null || !request.isValid()) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        ToppingPO toppingTypePO = convertToToppingDTO(request);
        try {
            if (request.isPutNew()) {
                return doPutNew(toppingTypePO);
            } else {
                return doPutUpdate(toppingTypePO);
            }
        } catch (Exception e) {
            log.error("put|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutNew(ToppingPO po) {
        try {
            ToppingPO exist = toppingAccessor.getByToppingCode(po.getTenantCode(), po.getToppingCode());
            if (exist != null) {
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
            }

            int inserted = toppingAccessor.insert(po);
            if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
                log.error("putNew|error|" + inserted);
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
            }

            // 删除菜单下发缓存
            menuDispatchCacheAccessor.clear(po.getTenantCode());

            return TeaMachineResult.success();
        } catch (Exception e) {
            log.error("putNew|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutUpdate(ToppingPO po) {
        try {
            ToppingPO exist = toppingAccessor.getByToppingCode(po.getTenantCode(), po.getToppingCode());
            if (exist == null) {
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_NOT_FOUND));
            }

            int updated = toppingAccessor.update(po);
            if (CommonConsts.DB_UPDATED_ONE_ROW != updated) {
                log.error("putUpdate|error|" + updated);
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
            }

            // 删除菜单下发缓存
            menuDispatchCacheAccessor.clear(po.getTenantCode());

            return TeaMachineResult.success();
        } catch (Exception e) {
            log.error("putUpdate|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> deleteByToppingCode(String tenantCode, String toppingCode) {
        if (StringUtils.isEmpty(tenantCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            int countByToppingCode = toppingBaseRuleMapper.countByToppingCode(tenantCode, toppingCode);
            if (countByToppingCode == CommonConsts.DB_SELECT_ZERO_ROW) {
                toppingAccessor.deleteByToppingCode(tenantCode, toppingCode);

                // 删除菜单下发缓存
                menuDispatchCacheAccessor.clear(tenantCode);

                return TeaMachineResult.success();
            } else {
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(
                        ErrorCodeEnum.BIZ_ERR_CANNOT_DELETE_USING_OBJECT));
            }
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }
}
