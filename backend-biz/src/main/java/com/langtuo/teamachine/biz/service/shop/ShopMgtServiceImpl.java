package com.langtuo.teamachine.biz.service.shop;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.shop.ShopDTO;
import com.langtuo.teamachine.api.request.shop.ShopPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.shop.ShopMgtService;
import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.biz.manager.OrgManager;
import com.langtuo.teamachine.biz.manager.ShopGroupManager;
import com.langtuo.teamachine.biz.util.SpringManagerUtils;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.accessor.device.MachineGroupAccessor;
import com.langtuo.teamachine.dao.accessor.user.AdminAccessor;
import com.langtuo.teamachine.dao.po.shop.ShopPO;
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

import static com.langtuo.teamachine.biz.convertor.shop.ShopMgtConvertor.convertToShopPO;

@Component
@Slf4j
public class ShopMgtServiceImpl implements ShopMgtService {
    @Resource
    private ShopAccessor shopAccessor;

    @Resource
    private AdminAccessor adminAccessor;

    @Resource
    private OrgAccessor orgAccessor;

    @Resource
    private MachineGroupAccessor machineGroupAccessor;

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<ShopDTO> getByShopCode(String tenantCode, String shopCode) {
        ShopPO shopPO = shopAccessor.getByShopCode(tenantCode, shopCode);
        ShopDTO shopDTO = convertToShopPO(shopPO);
        return TeaMachineResult.success(shopDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<ShopDTO>> search(String tenantCode, String shopName, String shopGroupCode,
            int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        try {
            List<String> shopGroupCodeListParam = Lists.newArrayList();
            if (StringUtils.isBlank(shopGroupCode)) {
                List<String> shopGroupCodeList = getShopGroupCodeListByLoginSession(tenantCode);
                if (CollectionUtils.isEmpty(shopGroupCodeList)) {
                    return TeaMachineResult.success(new PageDTO<>(null, 0, pageNum, pageSize));
                } else {
                    shopGroupCodeListParam.addAll(shopGroupCodeList);
                }
            } else {
                shopGroupCodeListParam.add(shopGroupCode);
            }

            PageInfo<ShopPO> pageInfo = shopAccessor.search(tenantCode, shopName, shopGroupCodeListParam,
                    pageNum, pageSize);

            return TeaMachineResult.success(new PageDTO<>(
                    convertToShopPO(pageInfo.getList()), pageInfo.getTotal(), pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<ShopDTO>> listByShopGroupCode(String tenantCode, String shopGroupCode) {
        if (StringUtils.isBlank(tenantCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            List<String> shopGroupCodeListParam = Lists.newArrayList();
            if (StringUtils.isBlank(shopGroupCode)) {
                List<String> shopGroupCodeList = getShopGroupCodeListByLoginSession(tenantCode);
                if (CollectionUtils.isEmpty(shopGroupCodeList)) {
                    return TeaMachineResult.success(null);
                } else {
                    shopGroupCodeListParam.addAll(shopGroupCodeList);
                }
            } else {
                shopGroupCodeListParam.add(shopGroupCode);
            }

            List<ShopPO> poList = shopAccessor.listByShopGroupCodeList(tenantCode, shopGroupCodeListParam);
            return TeaMachineResult.success(convertToShopPO(poList));
        } catch (Exception e) {
            log.error("listByShopGroupCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> put(ShopPutRequest request) {
        if (request == null || !request.isValid()) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        ShopPO shopPO = convertToShopPO(request);
        try {
            if (request.isPutNew()) {
                return doPutNew(shopPO);
            } else {
                return doPutUpdate(shopPO);
            }
        } catch (Exception e) {
            log.error("put|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutNew(ShopPO po) {
        ShopPO exist = shopAccessor.getByShopCode(po.getTenantCode(), po.getShopCode());
        if (exist != null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
        }

        int inserted = shopAccessor.insert(po);
        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
            log.error("putNew|error|" + inserted);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutUpdate(ShopPO po) {
        ShopPO exist = shopAccessor.getByShopCode(po.getTenantCode(), po.getShopCode());
        if (exist == null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_NOT_FOUND));
        }

        int updated = shopAccessor.update(po);
        if (CommonConsts.DB_UPDATED_ONE_ROW != updated) {
            log.error("putUpdate|error|" + updated);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
        return TeaMachineResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TeaMachineResult<Void> deleteByShopCode(String tenantCode, String shopGroupCode) {
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(shopGroupCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            shopAccessor.delete(tenantCode, shopGroupCode);
            return TeaMachineResult.success();
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TeaMachineResult<Integer> countByShopGroupCode(String tenantCode, String shopGroupCode) {
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(shopGroupCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            int cnt = shopAccessor.countByShopGroupCode(tenantCode, shopGroupCode);
            return TeaMachineResult.success(cnt);
        } catch (Exception e) {
            log.error("countByShopGroupCode|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    private List<String> getShopGroupCodeListByLoginSession(String tenantCode) {
        OrgManager orgManager = SpringManagerUtils.getOrgManager();
        List<String> orgNameList = orgManager.getOrgNameListByLoginSession(tenantCode);

        ShopGroupManager shopGroupManager = SpringManagerUtils.getShopGroupManager();
        List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByOrgNameList(tenantCode, orgNameList);

        return shopGroupCodeList;
    }
}
