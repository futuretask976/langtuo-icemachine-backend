package com.langtuo.teamachine.biz.convertor.drink;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.langtuo.teamachine.api.model.drink.*;
import com.langtuo.teamachine.api.request.drink.*;
import com.langtuo.teamachine.dao.accessor.drink.*;
import com.langtuo.teamachine.dao.po.drink.*;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TeaMgtConvertor {
    public static List<TeaDTO> convertToTeaDTO(List<TeaPO> poList, boolean needDetail) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<TeaDTO> list = poList.stream()
                .map(po -> convertToTeaDTO(po, needDetail))
                .collect(Collectors.toList());
        return list;
    }

    public static TeaDTO convertToTeaDTO(TeaPO po, boolean needDetail) {
        if (po == null) {
            return null;
        }

        TeaDTO dto = new TeaDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setTeaCode(po.getTeaCode());
        dto.setTeaName(po.getTeaName());
        dto.setState(po.getState());
        dto.setTeaTypeCode(po.getTeaTypeCode());
        dto.setComment(po.getComment());
        dto.setExtraInfo(po.getExtraInfo());
        dto.setImgLink(po.getImgLink());

        if (needDetail) {
            fillTeaDTO(po.getTenantCode(), dto);
        }
        return dto;
    }

    public static void fillTeaDTO(String tenantCode, TeaDTO teaDTO) {
        if (StringUtils.isBlank(tenantCode) || teaDTO == null) {
            return;
        }

        ToppingBaseRuleAccessor toppingBaseRuleAccessor = SpringAccessorUtils.getToppingBaseRuleAccessor();
        List<ToppingBaseRulePO> toppingBaseRulePOList = toppingBaseRuleAccessor.listByTeaCode(tenantCode,
                teaDTO.getTeaCode());
        if (CollectionUtils.isEmpty(toppingBaseRulePOList)) {
            // toppingBaseRulePOList 不应该为空
            return;
        }
        teaDTO.setToppingBaseRuleList(convertToToppingBaseRuleDTO(toppingBaseRulePOList));


        SpecItemRuleAccessor specItemRuleAccessor = SpringAccessorUtils.getTeaSpecItemAccessor();
        List<SpecItemRulePO> specItemRulePOList = specItemRuleAccessor.listByTeaCode(tenantCode, teaDTO.getTeaCode());
        if (CollectionUtils.isEmpty(specItemRulePOList)) {
            // teaSpecItemPOList 不应该为空
            return;
        }
        teaDTO.setSpecRuleList(convertToSpecRuleDTO(specItemRulePOList));

        TeaUnitAccessor teaUnitAccessor = SpringAccessorUtils.getTeaUnitAccessor();
        List<TeaUnitPO> teaUnitPOList = teaUnitAccessor.listByTeaCode(tenantCode, teaDTO.getTeaCode());
        if (CollectionUtils.isEmpty(teaUnitPOList)) {
            // teaUnitPOList 不应该为空
            return;
        }
        teaDTO.setTeaUnitList(convertToTeaUnitDTO(toppingBaseRulePOList, teaUnitPOList));
    }

    public static TeaPO convertToTeaPO(TeaPutRequest request) {
        if (request == null) {
            return null;
        }

        TeaPO po = new TeaPO();
        po.setTeaCode(request.getTeaCode());
        po.setTeaName(request.getTeaName());
        po.setState(request.getState());
        po.setTeaTypeCode(request.getTeaTypeCode());
        po.setComment(request.getComment());
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        po.setImgLink(request.getImgLink());
        return po;
    }

    public static List<TeaUnitDTO> convertToTeaUnitDTO(List<ToppingBaseRulePO> toppingBaseRulePOList,
            List<TeaUnitPO> teaUnitPOList) {
        if (CollectionUtils.isEmpty(teaUnitPOList)) {
            return null;
        }

        return teaUnitPOList.stream()
                .map(po -> convertToTeaUnitDTO(toppingBaseRulePOList, po))
                .collect(Collectors.toList());
    }

    public static TeaUnitDTO convertToTeaUnitDTO(List<ToppingBaseRulePO> toppingBaseRulePOList, TeaUnitPO teaUnitPO) {
        if (teaUnitPO == null) {
            return null;
        }

        TeaUnitDTO teaUnitDTO = new TeaUnitDTO();
        teaUnitDTO.setTeaUnitCode(teaUnitPO.getTeaUnitCode());
        teaUnitDTO.setTeaUnitName(teaUnitPO.getTeaUnitName());

        ToppingAdjustRuleAccessor toppingAdjustRuleAccessor = SpringAccessorUtils.getToppingAdjustRuleAccessor();
        List<ToppingAdjustRulePO> toppingAdjustRulePOList = toppingAdjustRuleAccessor.listByTeaUnitCode(
                teaUnitPO.getTenantCode(), teaUnitPO.getTeaCode(), teaUnitPO.getTeaUnitCode());
        teaUnitDTO.setToppingAdjustRuleList(convertToToppingAdjustRuleDTO(toppingBaseRulePOList,
                toppingAdjustRulePOList));

        return teaUnitDTO;
    }

    public static List<ToppingAdjustRuleDTO> convertToToppingAdjustRuleDTO(
            List<ToppingBaseRulePO> toppingBaseRulePOList, List<ToppingAdjustRulePO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        Map<String, ToppingBaseRulePO> toppingBaseRulePOMap = Maps.newHashMap();
        for (ToppingBaseRulePO toppingBaseRulePO : toppingBaseRulePOList) {
            toppingBaseRulePOMap.put(toppingBaseRulePO.getToppingCode(), toppingBaseRulePO);
        }

        return poList.stream()
                .map(po -> convertToToppingAdjustRuleDTO(toppingBaseRulePOMap, po))
                .collect(Collectors.toList());
    }

    public static ToppingAdjustRuleDTO convertToToppingAdjustRuleDTO(
            Map<String, ToppingBaseRulePO> toppingBaseRulePOMap, ToppingAdjustRulePO po) {
        if (po == null) {
            return null;
        }

        ToppingAdjustRuleDTO dto = new ToppingAdjustRuleDTO();
        dto.setStepIndex(po.getStepIndex());
        dto.setTeaUnitCode(po.getTeaUnitCode());
        dto.setToppingCode(po.getToppingCode());
        dto.setAdjustType(po.getAdjustType());
        dto.setAdjustMode(po.getAdjustMode());
        dto.setAdjustAmount(po.getAdjustAmount());

        ToppingAccessor toppingAccessor = SpringAccessorUtils.getToppingAccessor();
        ToppingPO toppingPO = toppingAccessor.getByToppingCode(po.getTenantCode(), po.getToppingCode());
        dto.setToppingName(toppingPO.getToppingName());
        dto.setMeasureUnit(toppingPO.getMeasureUnit());

        ToppingBaseRulePO toppingBaseRulePO = toppingBaseRulePOMap.get(po.getToppingCode());
        if (toppingBaseRulePO != null) {
            dto.setBaseAmount(toppingBaseRulePO.getBaseAmount());
        }

        return dto;
    }

    public static List<TeaUnitPO> convertToTeaUnitPO(TeaPutRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getTeaUnitList())) {
            return null;
        }

        List<TeaUnitPO> teaUnitPOList = Lists.newArrayList();
        for (TeaUnitPutRequest teaUnitPutRequest : request.getTeaUnitList()) {
            TeaUnitPO po = new TeaUnitPO();
            po.setTenantCode(request.getTenantCode());
            po.setTeaCode(request.getTeaCode());
            po.setTeaUnitCode(sortTeaUnitCode(teaUnitPutRequest.getTeaUnitCode()));
            po.setOuterTeaUnitCode(sortTeaUnitCode(teaUnitPutRequest.getOuterTeaUnitCode()));
            po.setTeaUnitName(teaUnitPutRequest.getTeaUnitName());
            teaUnitPOList.add(po);
        }
        return teaUnitPOList;
    }

    public static String sortTeaUnitCode(String teaUnitCode) {
        if (StringUtils.isBlank(teaUnitCode)) {
            return teaUnitCode;
        }

        String[] codeArr = teaUnitCode.split(CommonConsts.HORIZONTAL_BAR);
        Arrays.sort(codeArr, String.CASE_INSENSITIVE_ORDER);

        StringBuffer result = new StringBuffer();
        for (String str : codeArr) {
            if (result.length() > CommonConsts.COLLECTION_LENGTH_ZERO) {
                result.append(CommonConsts.HORIZONTAL_BAR);
            }
            result.append(str);
        }
        return result.toString();
    }

    public static List<ToppingAdjustRulePO> convertToToppingAdjustRulePO(TeaPutRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getTeaUnitList())) {
            return null;
        }

        List<ToppingAdjustRulePO> toppingAdjustRulePOList = Lists.newArrayList();
        for (TeaUnitPutRequest teaUnitPutRequest : request.getTeaUnitList()) {
            for (ToppingAdjustRulePutRequest toppingAdjustRulePutRequest : teaUnitPutRequest.getToppingAdjustRuleList()) {
                ToppingAdjustRulePO po = new ToppingAdjustRulePO();
                po.setTenantCode(request.getTenantCode());
                po.setTeaCode(request.getTeaCode());
                po.setTeaUnitCode(sortTeaUnitCode(teaUnitPutRequest.getTeaUnitCode()));
                po.setStepIndex(toppingAdjustRulePutRequest.getStepIndex());
                po.setToppingCode(toppingAdjustRulePutRequest.getToppingCode());
                po.setAdjustType(toppingAdjustRulePutRequest.getAdjustType());
                po.setAdjustMode(toppingAdjustRulePutRequest.getAdjustMode());
                po.setAdjustAmount(toppingAdjustRulePutRequest.getAdjustAmount());
                toppingAdjustRulePOList.add(po);
            }
        }
        return toppingAdjustRulePOList;
    }

    public static List<ToppingBaseRulePO> convertToToppingBaseRuleDTO(TeaPutRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getTeaUnitList())) {
            return null;
        }

        List<ToppingBaseRulePO> toppingBaseRulePOList = Lists.newArrayList();
        for (ToppingBaseRulePutRequest toppingBaseRulePutRequest : request.getToppingBaseRuleList()) {
            ToppingBaseRulePO toppingBaseRulePO = new ToppingBaseRulePO();
            toppingBaseRulePO.setTenantCode(request.getTenantCode());
            toppingBaseRulePO.setTeaCode(request.getTeaCode());
            toppingBaseRulePO.setStepIndex(toppingBaseRulePutRequest.getStepIndex());
            toppingBaseRulePO.setToppingCode(toppingBaseRulePutRequest.getToppingCode());
            toppingBaseRulePO.setBaseAmount(toppingBaseRulePutRequest.getBaseAmount());
            toppingBaseRulePOList.add(toppingBaseRulePO);
        }
        return toppingBaseRulePOList;
    }

    public static List<SpecItemRulePO> convertToTeaSpecItemPO(TeaPutRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getTeaUnitList())) {
            return null;
        }

        List<SpecItemRulePO> specItemRulePOList = Lists.newArrayList();
        for (SpecRulePutRequest specRulePutRequest : request.getSpecRuleList()) {
            for (SpecItemRulePutRequest specItemRulePutRequest : specRulePutRequest.getSpecItemRuleList()) {
                SpecItemRulePO specItemRulePO = new SpecItemRulePO();
                specItemRulePO.setTenantCode(request.getTenantCode());
                specItemRulePO.setTeaCode(request.getTeaCode());
                specItemRulePO.setSpecCode(specRulePutRequest.getSpecCode());
                specItemRulePO.setSpecItemCode(specItemRulePutRequest.getSpecItemCode());
                specItemRulePOList.add(specItemRulePO);
            }
        }
        return specItemRulePOList;
    }

    public static List<ToppingBaseRuleDTO> convertToToppingBaseRuleDTO(List<ToppingBaseRulePO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<ToppingBaseRuleDTO> dtoList = poList.stream()
                .map(po -> convertToToppingBaseRuleDTO(po))
                .collect(Collectors.toList());
        dtoList.sort((o1, o2) -> o1.getStepIndex() - o2.getStepIndex());

        return dtoList;
    }

    public static ToppingBaseRuleDTO convertToToppingBaseRuleDTO(ToppingBaseRulePO po) {
        if (po == null) {
            return null;
        }

        ToppingBaseRuleDTO dto = new ToppingBaseRuleDTO();
        dto.setStepIndex(po.getStepIndex());
        dto.setToppingCode(po.getToppingCode());
        dto.setBaseAmount(po.getBaseAmount());

        ToppingAccessor toppingAccessor = SpringAccessorUtils.getToppingAccessor();
        ToppingPO toppingPO = toppingAccessor.getByToppingCode(po.getTenantCode(), po.getToppingCode());
        if (toppingPO != null) {
            dto.setToppingName(toppingPO.getToppingName());
            dto.setMeasureUnit(toppingPO.getMeasureUnit());
            dto.setMeasureUnit(toppingPO.getMeasureUnit());
        }
        return dto;
    }

    public static List<SpecRuleDTO> convertToSpecRuleDTO(List<SpecItemRulePO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        Map<String, SpecRuleDTO> specRuleDTOMap = Maps.newHashMap();
        for (SpecItemRulePO po : poList) {
            SpecRuleDTO specRuleDTO = specRuleDTOMap.get(po.getSpecCode());
            if (specRuleDTO == null) {
                specRuleDTO = new SpecRuleDTO();
                specRuleDTO.setSpecCode(po.getSpecCode());

                SpecPO specPO = SpringAccessorUtils.getSpecAccessor().getBySpecCode(po.getTenantCode(),
                        po.getSpecCode());
                if (specPO != null) {
                    specRuleDTO.setSpecName(specPO.getSpecName());
                }
                specRuleDTOMap.put(specRuleDTO.getSpecCode(), specRuleDTO);
            }
            specRuleDTO.addSpecItemRule(convertToSpecItemRuleDTO(specRuleDTO, po));
        }

        List<SpecRuleDTO> resultList = Lists.newArrayList();
        for (Map.Entry<String, SpecRuleDTO> entry : specRuleDTOMap.entrySet()) {
            resultList.add(entry.getValue());
        }
        return resultList;
    }

    public static SpecItemRuleDTO convertToSpecItemRuleDTO(SpecRuleDTO specRuleDTO, SpecItemRulePO specItemRulePO) {
        if (specItemRulePO == null) {
            return null;
        }

        SpecItemRuleDTO dto = new SpecItemRuleDTO();
        dto.setSpecCode(specItemRulePO.getSpecCode());
        dto.setSpecName(specRuleDTO.getSpecName());
        dto.setSpecItemCode(specItemRulePO.getSpecItemCode());

        SpecItemAccessor specItemAccessor = SpringAccessorUtils.getSpecItemAccessor();
        SpecItemPO specItemPO = specItemAccessor.getBySpecItemCode(specItemRulePO.getTenantCode(),
                specItemRulePO.getSpecItemCode());
        if (specItemPO != null) {
            dto.setSpecItemName(specItemPO.getSpecItemName());
        }
        return dto;
    }

    public static SpecItemRulePutRequest convertToSpecItemRulePutRequest(SpecItemPO po) {
        if (po == null) {
            return null;
        }

        SpecItemRulePutRequest specItemRulePutRequest = new SpecItemRulePutRequest();
        specItemRulePutRequest.setSpecCode(po.getSpecCode());
        specItemRulePutRequest.setSpecItemCode(po.getSpecItemCode());
        return specItemRulePutRequest;
    }
}
