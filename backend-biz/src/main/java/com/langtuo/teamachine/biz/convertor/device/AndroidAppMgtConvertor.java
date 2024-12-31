package com.langtuo.teamachine.biz.convertor.device;

import com.langtuo.teamachine.api.model.device.AndroidAppDTO;
import com.langtuo.teamachine.api.request.device.AndroidAppDispatchPutRequest;
import com.langtuo.teamachine.api.request.device.AndroidAppPutRequest;
import com.langtuo.teamachine.dao.po.device.AndroidAppDispatchPO;
import com.langtuo.teamachine.dao.po.device.AndroidAppPO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class AndroidAppMgtConvertor {
    public static List<AndroidAppDTO> convertToAndroidAppDTO(List<AndroidAppPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        return poList.stream()
                .map(po -> convertToAndroidAppDTO(po))
                .collect(Collectors.toList());
    }

    public static AndroidAppDTO convertToAndroidAppDTO(AndroidAppPO po) {
        if (po == null) {
            return null;
        }

        AndroidAppDTO dto = new AndroidAppDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setExtraInfo(po.getExtraInfo());
        dto.setVersion(po.getVersion());
        dto.setOssPath(po.getOssPath());
        dto.setComment(po.getComment());
        return dto;
    }

    public static AndroidAppPO convertToAndroidAppPO(AndroidAppPutRequest request) {
        if (request == null) {
            return null;
        }

        AndroidAppPO po = new AndroidAppPO();
        po.setExtraInfo(request.getExtraInfo());
        po.setVersion(request.getVersion());
        po.setOssPath(request.getOssPath());
        po.setComment(request.getComment());
        return po;
    }

    public static List<AndroidAppDispatchPO> convertToAndroidAppDispatchPO(AndroidAppDispatchPutRequest request) {
        if (request == null) {
            return null;
        }

        String tenantCode = request.getTenantCode();
        String version = request.getVersion();

        return request.getShopGroupCodeList().stream()
                .map(shopGroupCode -> {
                    AndroidAppDispatchPO po = new AndroidAppDispatchPO();
                    po.setTenantCode(tenantCode);
                    po.setVersion(version);
                    po.setShopGroupCode(shopGroupCode);
                    return po;
                }).collect(Collectors.toList());
    }
}
