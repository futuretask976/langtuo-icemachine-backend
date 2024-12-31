package com.langtuo.teamachine.biz.service.menu;

import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.menu.SeriesDTO;
import com.langtuo.teamachine.api.request.menu.SeriesPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.menu.SeriesMgtService;
import com.langtuo.teamachine.dao.accessor.menu.MenuDispatchCacheAccessor;
import com.langtuo.teamachine.dao.accessor.menu.MenuSeriesRelAccessor;
import com.langtuo.teamachine.dao.accessor.menu.SeriesAccessor;
import com.langtuo.teamachine.dao.accessor.menu.SeriesTeaRelAccessor;
import com.langtuo.teamachine.dao.po.menu.SeriesPO;
import com.langtuo.teamachine.dao.po.menu.SeriesTeaRelPO;
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

import static com.langtuo.teamachine.biz.convertor.menu.SeriesMgtConvertor.*;

@Component
@Slf4j
public class SeriesMgtServiceImpl implements SeriesMgtService {
    @Resource
    private SeriesAccessor seriesAccessor;

    @Resource
    private SeriesTeaRelAccessor seriesTeaRelAccessor;

    @Resource
    private MenuSeriesRelAccessor menuSeriesRelAccessor;

    @Resource
    private MenuDispatchCacheAccessor menuDispatchCacheAccessor;

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<List<SeriesDTO>> list(String tenantCode) {
        TeaMachineResult<List<SeriesDTO>> teaMachineResult;
        try {
            List<SeriesPO> poList = seriesAccessor.list(tenantCode);
            List<SeriesDTO> dtoList = convertToSeriesDTO(poList);
            teaMachineResult = TeaMachineResult.success(dtoList);
        } catch (Exception e) {
            log.error("list|fatal|e=" + e.getMessage(), e);
            teaMachineResult = TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
        return teaMachineResult;
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<SeriesDTO>> search(String tenantName, String seriesCode, String seriesName,
            int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        TeaMachineResult<PageDTO<SeriesDTO>> teaMachineResult;
        try {
            PageInfo<SeriesPO> pageInfo = seriesAccessor.search(tenantName, seriesCode, seriesName,
                    pageNum, pageSize);
            List<SeriesDTO> dtoList = convertToSeriesDTO(pageInfo.getList());

            teaMachineResult = TeaMachineResult.success(new PageDTO<>(dtoList, pageInfo.getTotal(),
                    pageNum, pageSize));
        } catch (Exception e) {
            log.error("search|fatal|e=" + e.getMessage(), e);
            teaMachineResult = TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
        return teaMachineResult;
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<SeriesDTO> getBySeriesCode(String tenantCode, String seriesCode) {
        TeaMachineResult<SeriesDTO> teaMachineResult;
        try {
            SeriesPO po = seriesAccessor.getBySeriesCode(tenantCode, seriesCode);
            SeriesDTO dto = convertToSeriesDTO(po);
            teaMachineResult = TeaMachineResult.success(dto);
        } catch (Exception e) {
            log.error("getByCode|fatal|e=" + e.getMessage(), e);
            teaMachineResult = TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
        return teaMachineResult;
    }

    @Override
    public TeaMachineResult<Void> put(SeriesPutRequest request) {
        if (request == null || !request.isValid()) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        SeriesPO seriesPO = convertSeriesPO(request);
        List<SeriesTeaRelPO> seriesTeaRelPOList = convertToSeriesTeaRelPO(request);
        try {
            if (request.isPutNew()) {
                return doPutNew(seriesPO, seriesTeaRelPOList);
            } else {
                return doPutUpdate(seriesPO, seriesTeaRelPOList);
            }
        } catch (Exception e) {
            log.error("put|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }
    }

    @Override
    public TeaMachineResult<Void> deleteBySeriesCode(String tenantCode, String seriesCode) {
        if (StringUtils.isEmpty(tenantCode)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        try {
            return doDeleteBySeriesCode(tenantCode, seriesCode);
        } catch (Exception e) {
            log.error("delete|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doDeleteBySeriesCode(String tenantCode, String seriesCode) {
        int count = menuSeriesRelAccessor.countBySeriesCode(tenantCode, seriesCode);
        if (CommonConsts.DB_SELECT_ZERO_ROW != count) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(
                    ErrorCodeEnum.BIZ_ERR_CANNOT_DELETE_USING_OBJECT));
        }

        seriesAccessor.deleteBySeriesCode(tenantCode, seriesCode);
        seriesTeaRelAccessor.deleteBySeriesCode(tenantCode, seriesCode);

        // 删除菜单下发缓存
        menuDispatchCacheAccessor.clear(tenantCode);

        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutNew(SeriesPO po, List<SeriesTeaRelPO> teaRelPOList) {
        SeriesPO exist = seriesAccessor.getBySeriesCode(po.getTenantCode(), po.getSeriesCode());
        if (exist != null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_CODE_DUPLICATED));
        }

        int inserted = seriesAccessor.insert(po);
        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
            log.error("putNewSeries|error|" + inserted);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }

        seriesTeaRelAccessor.deleteBySeriesCode(po.getTenantCode(), po.getSeriesCode());
        int inserted4TeaRel = seriesTeaRelAccessor.insertBatch(teaRelPOList);
        if (inserted4TeaRel != teaRelPOList.size()) {
            log.error("putNewTeaRel|error|" + inserted4TeaRel);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }

        // 删除菜单下发缓存
        menuDispatchCacheAccessor.clear(po.getTenantCode());

        return TeaMachineResult.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private TeaMachineResult<Void> doPutUpdate(SeriesPO po, List<SeriesTeaRelPO> teaRelPOList) {
        SeriesPO exist = seriesAccessor.getBySeriesCode(po.getTenantCode(), po.getSeriesCode());
        if (exist == null) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_OBJECT_NOT_FOUND));
        }

        int updated = seriesAccessor.update(po);
        if (CommonConsts.DB_UPDATED_ONE_ROW != updated) {
            log.error("putUpdateSeries|error|" + updated);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_UPDATE_FAIL));
        }

        seriesTeaRelAccessor.deleteBySeriesCode(po.getTenantCode(), po.getSeriesCode());
        int inserted4TeaRel = seriesTeaRelAccessor.insertBatch(teaRelPOList);
        if (inserted4TeaRel != teaRelPOList.size()) {
            log.error("putUpdateTeaRel|error|" + inserted4TeaRel);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_INSERT_FAIL));
        }

        // 删除菜单下发缓存
        menuDispatchCacheAccessor.clear(po.getTenantCode());

        return TeaMachineResult.success();
    }
}
