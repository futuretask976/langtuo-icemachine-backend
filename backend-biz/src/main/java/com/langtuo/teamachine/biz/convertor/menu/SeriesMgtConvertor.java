package com.langtuo.teamachine.biz.convertor.menu;

import com.langtuo.teamachine.api.model.menu.SeriesDTO;
import com.langtuo.teamachine.api.model.menu.SeriesTeaRelDTO;
import com.langtuo.teamachine.api.request.menu.SeriesPutRequest;
import com.langtuo.teamachine.dao.accessor.menu.SeriesTeaRelAccessor;
import com.langtuo.teamachine.dao.po.menu.SeriesPO;
import com.langtuo.teamachine.dao.po.menu.SeriesTeaRelPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class SeriesMgtConvertor {
    public static List<SeriesDTO> convertToSeriesDTO(List<SeriesPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<SeriesDTO> list = poList.stream()
                .map(po -> convertToSeriesDTO(po))
                .collect(Collectors.toList());
        return list;
    }

    public static SeriesDTO convertToSeriesDTO(SeriesPO po) {
        if (po == null) {
            return null;
        }

        SeriesDTO dto = new SeriesDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setComment(po.getComment());
        dto.setExtraInfo(po.getExtraInfo());
        dto.setSeriesCode(po.getSeriesCode());
        dto.setSeriesName(po.getSeriesName());

        SeriesTeaRelAccessor seriesTeaRelAccessor = SpringAccessorUtils.getSeriesTeaRelAccessor();
        List<SeriesTeaRelPO> seriesTeaRelPOList = seriesTeaRelAccessor.listBySeriesCode(
                po.getTenantCode(), po.getSeriesCode());
        dto.setSeriesTeaRelList(convertToSeriesTeaRelDTO(seriesTeaRelPOList));
        return dto;
    }

    public static SeriesPO convertSeriesPO(SeriesPutRequest request) {
        if (request == null) {
            return null;
        }

        SeriesPO po = new SeriesPO();
        po.setTenantCode(request.getTenantCode());
        po.setComment(request.getComment());
        po.setExtraInfo(request.getExtraInfo());
        po.setSeriesCode(request.getSeriesCode());
        po.setSeriesName(request.getSeriesName());
        return po;
    }

    public static List<SeriesTeaRelDTO> convertToSeriesTeaRelDTO(List<SeriesTeaRelPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        return poList.stream().map(po -> {
            SeriesTeaRelDTO dto = new SeriesTeaRelDTO();
            dto.setSeriesCode(po.getSeriesCode());
            dto.setTeaCode(po.getTeaCode());
            return dto;
        }).collect(Collectors.toList());
    }

    public static List<SeriesTeaRelPO> convertToSeriesTeaRelPO(SeriesPutRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getSeriesTeaRelList())) {
            return null;
        }

        return request.getSeriesTeaRelList().stream()
                .map(seriesTeaRelPutRequest -> {
                    SeriesTeaRelPO po = new SeriesTeaRelPO();
                    po.setTenantCode(request.getTenantCode());
                    po.setSeriesCode(seriesTeaRelPutRequest.getSeriesCode());
                    po.setTeaCode(seriesTeaRelPutRequest.getTeaCode());
                    return po;
                }).collect(Collectors.toList());
    }
}
