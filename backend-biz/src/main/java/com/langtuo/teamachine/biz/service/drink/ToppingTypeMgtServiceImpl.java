package com.langtuo.teamachine.biz.service.drink;

import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.ToppingTypeDTO;
import com.langtuo.teamachine.api.request.drink.ToppingTypePutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.drink.ToppingTypeMgtService;
import com.langtuo.teamachine.dao.accessor.drink.ToppingAccessor;
import com.langtuo.teamachine.dao.accessor.drink.ToppingTypeAccessor;
import com.langtuo.teamachine.dao.po.drink.ToppingTypePO;
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

import static com.langtuo.teamachine.biz.convertor.drink.ToppingTypeMgtConvertor.convertToToppingTypeDTO;

@Component
@Slf4j
public class ToppingTypeMgtServiceImpl implements ToppingTypeMgtService {
    @Resource
    private ToppingTypeAccessor toppingTypeAccessor;

    @Resource
    private ToppingAccessor toppingAccessor;

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<ToppingTypeDTO>> list(String tenantCode) {
        try {
            List<ToppingTypePO> poList = toppingTypeAccessor.selectList(tenantCode);
            List<ToppingTypeDTO> dtoList = convertToToppingTypeDTO(poList);

            return TeaMachineResult.success(dtoList);
        } catch (Exception e) {
            log.error("list|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<ToppingTypeDTO>> search(String tenantName, String toppingTypeCode,
            String toppingTypeName, int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;
        
        try {
            PageInfo<ToppingTypePO> pageInfo = toppingTypeAccessor.search(tenantName, toppingTypeCode, toppingTypeName,
                    pageNum, pageSize);
            List<ToppingTypeDTO> dtoList = convertToToppingTypeDTO(pageInfo.getList());

            return TeaMachineResult.success(new PageDTO<>(dtoList, pageInfo.getTotal(),
                    pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<ToppingTypeDTO> getByToppingTypeCode(String tenantCode, String toppingTypeCode) {
        try {
            ToppingTypePO po = toppingTypeAccessor.getByToppingTypeCode(tenantCode, toppingTypeCode);
            ToppingTypeDTO dto = convertToToppingTypeDTO(po);

            return TeaMachineResult.success(dto);
        } catch (Exception e) {
            log.error("getByCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> put(ToppingTypePutRequest request) {
        if (request == null || !request.isValid()) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        ToppingTypePO toppingTypePO = convertToToppingTypeDTO(request);
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
    private TeaMachineResult<Void> doPutNew(ToppingTypePO po) {
        ToppingTypePO exist = toppingTypeAccessor.getByToppingTypeCode(po.getTenantCode(), po.getToppingTypeCode());
        if (exist != null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
        }

        int inserted = toppingTypeAccessor.insert(po);
        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
            log.error("putNew|error|" + inserted);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutUpdate(ToppingTypePO po) {
        ToppingTypePO exist = toppingTypeAccessor.getByToppingTypeCode(po.getTenantCode(), po.getToppingTypeCode());
        if (exist == null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_NOT_FOUND));
        }

        int updated = toppingTypeAccessor.update(po);
        if (CommonConsts.DB_UPDATED_ONE_ROW != updated) {
            log.error("putUpdate|error|" + updated);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
        return TeaMachineResult.success();
    }

    @Override
    public TeaMachineResult<Void> deleteByToppingTypeCode(String tenantCode, String toppingTypeCode) {
        if (StringUtils.isEmpty(tenantCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            return doDeleteByToppingTypeCode(tenantCode, toppingTypeCode);
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    private TeaMachineResult<Void> doDeleteByToppingTypeCode(String tenantCode, String toppingTypeCode) {
        int countByToppingTypeCode = toppingAccessor.countByToppingTypeCode(tenantCode, toppingTypeCode);
        if (CommonConsts.DB_SELECT_ZERO_ROW != countByToppingTypeCode) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(
                    ErrorCodeEnum.BIZ_ERR_CANNOT_DELETE_USING_OBJECT));
        }
        toppingTypeAccessor.delete(tenantCode, toppingTypeCode);
        return TeaMachineResult.success();
    }
}
