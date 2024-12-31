package com.langtuo.teamachine.biz.service.drink;

import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.SpecDTO;
import com.langtuo.teamachine.api.request.drink.SpecPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.drink.SpecMgtService;
import com.langtuo.teamachine.dao.accessor.drink.SpecAccessor;
import com.langtuo.teamachine.dao.accessor.drink.SpecItemAccessor;
import com.langtuo.teamachine.dao.accessor.drink.SpecItemRuleAccessor;
import com.langtuo.teamachine.dao.accessor.menu.MenuDispatchCacheAccessor;
import com.langtuo.teamachine.dao.po.drink.SpecItemPO;
import com.langtuo.teamachine.dao.po.drink.SpecPO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.langtuo.teamachine.biz.convertor.drink.SpecMgtConvertor.convertToSpecDTO;
import static com.langtuo.teamachine.biz.convertor.drink.SpecMgtConvertor.convertToSpecItemPO;

@Component
@Slf4j
public class SpecMgtServiceImpl implements SpecMgtService {
    @Resource
    private SpecAccessor specAccessor;

    @Resource
    private SpecItemAccessor specItemAccessor;

    @Resource
    private SpecItemRuleAccessor specItemRuleAccessor;

    @Resource
    private MenuDispatchCacheAccessor menuDispatchCacheAccessor;

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<SpecDTO>> list(String tenantCode) {
        try {
            List<SpecPO> poList = specAccessor.list(tenantCode);
            List<SpecDTO> dtoList = convertToSpecDTO(poList);

            return TeaMachineResult.success(dtoList);
        } catch (Exception e) {
            log.error("list|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<SpecDTO>> search(String tenantName, String specCode, String specName,
            int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            PageInfo<SpecPO> pageInfo = specAccessor.search(tenantName, specCode, specName,
                    pageNum, pageSize);
            List<SpecDTO> dtoList = convertToSpecDTO(pageInfo.getList());

            return TeaMachineResult.success(new PageDTO<>(dtoList, pageInfo.getTotal(),
                    pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<SpecDTO> getBySpecCode(String tenantCode, String specCode) {
        try {
            SpecPO po = specAccessor.getBySpecCode(tenantCode, specCode);
            SpecDTO dto = convertToSpecDTO(po);

            return TeaMachineResult.success(dto);
        } catch (Exception e) {
            log.error("getByCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> put(SpecPutRequest request) {
        if (request == null || !request.isValid()) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        SpecPO po = convertToSpecDTO(request);
        List<SpecItemPO> specItemPOList = convertToSpecItemPO(request);
        try {
            if (request.isPutNew()) {
                return doPutNew(po, specItemPOList);
            } else {
                return doPutUpdate(po, specItemPOList);
            }
        } catch (Exception e) {
            log.error("put|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> deleteBySpecCode(String tenantCode, String specCode) {
        if (StringUtils.isEmpty(tenantCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            return doDeleteBySpecCode(tenantCode, specCode);
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutNew(SpecPO po, List<SpecItemPO> specItemPOList) {
        try {
            SpecPO exist = specAccessor.getBySpecCode(po.getTenantCode(), po.getSpecCode());
            if (exist != null) {
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
            }

            int inserted4Spec = specAccessor.insert(po);
            if (CommonConsts.DB_INSERTED_ONE_ROW != inserted4Spec) {
                log.error("putNewSpec|error|inserted=" + inserted4Spec);
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
            }

            specItemAccessor.deleteBySpecCode(po.getTenantCode(), po.getSpecCode());
            int inserted4SpecItem = specItemAccessor.insertBatch(specItemPOList);
            if (inserted4SpecItem != specItemPOList.size()) {
                log.error("putNewSpecItem|error|inserted4Item=" + inserted4SpecItem);
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
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
    private TeaMachineResult<Void> doPutUpdate(SpecPO po, List<SpecItemPO> specItemPOList) {
        try {
            SpecPO existSpecPO = specAccessor.getBySpecCode(po.getTenantCode(), po.getSpecCode());
            if (existSpecPO == null) {
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_NOT_FOUND));
            }
            List<SpecItemPO> existSpecItemPOList = specItemAccessor.listBySpecCode(po.getTenantCode(), po.getSpecCode());
            if (!CollectionUtils.isEmpty(existSpecItemPOList)) {
                List<SpecItemPO> deletedSpecItemPOList = filterDeletedSpecItemList(existSpecItemPOList, specItemPOList);
                if (!CollectionUtils.isEmpty(deletedSpecItemPOList)) {
                    int count = specItemRuleAccessor.countBySpecItemCode(po.getTenantCode(),
                            deletedSpecItemPOList.stream()
                            .map(SpecItemPO::getSpecItemCode)
                            .collect(Collectors.toList()));
                    if (count > CommonConsts.DB_COUNT_RESULT_ZERO) {
                        return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(
                                ErrorCodeEnum.BIZ_ERR_CANNOT_DELETE_USING_OBJECT));
                    }
                }
            }

            int updated = specAccessor.update(po);
            if (CommonConsts.DB_UPDATED_ONE_ROW != updated) {
                log.error("putUpdateSpec|error|updated=" + updated);
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
            }

            specItemAccessor.deleteBySpecCode(po.getTenantCode(), po.getSpecCode());
            int inserted4SpecItem = specItemAccessor.insertBatch(specItemPOList);
            if (inserted4SpecItem != specItemPOList.size()) {
                log.error("putUpdateSpecItem|error|inserted4Item=" + inserted4SpecItem);
                return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
            }

            // 删除菜单下发缓存
            menuDispatchCacheAccessor.clear(po.getTenantCode());

            return TeaMachineResult.success();
        } catch (Exception e) {
            log.error("putUpdate|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doDeleteBySpecCode(String tenantCode, String specCode) {
        int countBySpecCode = specItemRuleAccessor.countBySpecCode(tenantCode, specCode);
        if (countBySpecCode != CommonConsts.DB_SELECT_ZERO_ROW) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(
                    ErrorCodeEnum.BIZ_ERR_CANNOT_DELETE_USING_OBJECT));
        }
        specAccessor.deleteBySpecCode(tenantCode, specCode);
        specItemAccessor.deleteBySpecCode(tenantCode, specCode);

        // 删除菜单下发缓存
        menuDispatchCacheAccessor.clear(tenantCode);

        return TeaMachineResult.success();
    }

    private List<SpecItemPO> filterDeletedSpecItemList(List<SpecItemPO> existSpecItemPOList,
            List<SpecItemPO> newSpecItemPOList) {
        List<SpecItemPO> deleted = Lists.newArrayList();
        for (SpecItemPO exist : existSpecItemPOList) {
            boolean existNew = false;
            for (SpecItemPO newSpecItemPO : newSpecItemPOList) {
                if (exist.getSpecItemCode().equals(newSpecItemPO.getSpecItemCode())) {
                    existNew = true;
                    break;
                }
            }
            if (!existNew) {
                deleted.add(exist);
            }
        }
        return deleted;
    }
}
