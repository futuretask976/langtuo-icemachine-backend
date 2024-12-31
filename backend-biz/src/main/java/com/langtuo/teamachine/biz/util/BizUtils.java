package com.langtuo.teamachine.biz.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.langtuo.teamachine.api.model.drink.TeaDTO;
import com.langtuo.teamachine.api.model.menu.MenuDTO;
import com.langtuo.teamachine.api.model.menu.MenuSeriesRelDTO;
import com.langtuo.teamachine.api.model.menu.SeriesDTO;
import com.langtuo.teamachine.api.model.menu.SeriesTeaRelDTO;
import com.langtuo.teamachine.biz.convertor.drink.TeaMgtConvertor;
import com.langtuo.teamachine.biz.convertor.menu.MenuMgtConvertor;
import com.langtuo.teamachine.biz.convertor.menu.SeriesMgtConvertor;
import com.langtuo.teamachine.dao.accessor.drink.TeaAccessor;
import com.langtuo.teamachine.dao.accessor.menu.MenuAccessor;
import com.langtuo.teamachine.dao.accessor.menu.SeriesAccessor;
import com.langtuo.teamachine.dao.oss.OssUtils;
import com.langtuo.teamachine.dao.po.drink.TeaPO;
import com.langtuo.teamachine.dao.po.menu.MenuPO;
import com.langtuo.teamachine.dao.po.menu.SeriesPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.AliyunConsts;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
public class BizUtils {
    public static String getMenuFileName(String menuCode, String menuGmtModifiedYMDHMS) {
        String fileName = CommonConsts.MENU_OUTPUT_FILENAME_PREFIX + menuCode
                + CommonConsts.HORIZONTAL_BAR + menuGmtModifiedYMDHMS
                + CommonConsts.MENU_OUTPUT_PATH_EXT;
        return fileName;
    }

    public static String getMenuListFileName(String shopGroupCode) {
        String fileName = CommonConsts.MENU_OUTPUT_FILENAME_PREFIX + shopGroupCode
                + CommonConsts.MENU_OUTPUT_PATH_EXT;
        return fileName;
    }

    public static JSONObject getMenuDispatchCont(String tenantCode, String menuCode) {
        // 获取菜单
        MenuAccessor menuAccessor = SpringAccessorUtils.getMenuAccessor();
        MenuPO menuPO = menuAccessor.getByMenuCode(tenantCode, menuCode);
        MenuDTO menuDTO = MenuMgtConvertor.convertToMenuDTO(menuPO);
        if (menuDTO == null || CollectionUtils.isEmpty(menuDTO.getMenuSeriesRelList())) {
            log.error("getMenu|menuNullOrSeriesRelListNull|stopWorker|" + tenantCode + "|" + menuCode);
            return null;
        }

        // 获取系列列表
        List<SeriesDTO> seriesDTOList = Lists.newArrayList();
        for (MenuSeriesRelDTO menuSeriesRelDTO : menuDTO.getMenuSeriesRelList()) {
            SeriesAccessor seriesAccessor = SpringAccessorUtils.getSeriesAccessor();
            SeriesPO seriesPO = seriesAccessor.getBySeriesCode(tenantCode, menuSeriesRelDTO.getSeriesCode());
            SeriesDTO seriesDTO = SeriesMgtConvertor.convertToSeriesDTO(seriesPO);
            if (seriesDTO == null || CollectionUtils.isEmpty(seriesDTO.getSeriesTeaRelList())) {
                continue;
            }
            seriesDTOList.add(seriesDTO);
        }
        if (CollectionUtils.isEmpty(seriesDTOList)) {
            log.error("getSeriesList|seriesDTOListEmpty|stopWorker|" + tenantCode + "|" + menuCode);
            return null;
        }

        // 获取茶品编码列表
        Map<String, List<String>> teaCodeListMap = Maps.newHashMap();
        for (SeriesDTO seriesDTO : seriesDTOList) {
            teaCodeListMap.putIfAbsent(seriesDTO.getSeriesCode(), Lists.newArrayList());
            for (SeriesTeaRelDTO seriesTeaRelDTO : seriesDTO.getSeriesTeaRelList()) {
                teaCodeListMap.get(seriesDTO.getSeriesCode()).add(seriesTeaRelDTO.getTeaCode());
            }
        }
        if (CollectionUtils.isEmpty(teaCodeListMap)) {
            log.error("getTeaList|teaCodeListMapEmpty|stopWorker|" + tenantCode + "|" + menuCode);
            return null;
        }

        // 获取茶品列表
        Map<String, List<TeaDTO>> teaListMap = Maps.newHashMap();
        for (Map.Entry<String, List<String>> entry : teaCodeListMap.entrySet()) {
            String seriesCode = entry.getKey();
            teaListMap.putIfAbsent(seriesCode, Lists.newArrayList());

            List<String> teaCodeList = entry.getValue();
            if (CollectionUtils.isEmpty(teaCodeList)) {
                continue;
            }
            for (String teaCode : teaCodeList) {
                TeaAccessor teaAccessor = SpringAccessorUtils.getTeaAccessor();
                TeaPO teaPO = teaAccessor.getByTeaCode(tenantCode, teaCode);
                TeaDTO teaDTO = TeaMgtConvertor.convertToTeaDTO(teaPO, true);
                teaListMap.get(seriesCode).add(teaDTO);
            }
        }

        // 拼接需要输出的内容
        JSONObject jsonMenu = (JSONObject) JSON.toJSON(menuDTO);
        jsonMenu.remove("menuSeriesRelList");
        jsonMenu.put("seriesList", new JSONArray());
        for (SeriesDTO seriesDTO : seriesDTOList) {
            JSONObject seriesJSON = (JSONObject) JSON.toJSON(seriesDTO);
            seriesJSON.remove("seriesTeaRelList");
            seriesJSON.put("teaList", new JSONArray());
            for (TeaDTO teaDTO : teaListMap.get(seriesDTO.getSeriesCode())) {
                JSONObject jsonTea = (JSONObject) JSON.toJSON(teaDTO);
                seriesJSON.getJSONArray("teaList").add(jsonTea);
            }
            jsonMenu.getJSONArray("seriesList").add(seriesJSON);
        }
        return jsonMenu;
    }

    /**
     *
     * @param length
     * @return
     */
    public static String genRandomStr(int length) {
        SecureRandom random = new SecureRandom();
        random.setSeed(System.currentTimeMillis());

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CommonConsts.DEPLOY_CHAR_SET.length());
            char randomChar = CommonConsts.DEPLOY_CHAR_SET.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static boolean writeStrToFile(String outputCont, File outputFile) {
        if (StringUtils.isBlank(outputCont) || outputCont == null) {
            return false;
        }

        if (outputFile.exists()) {
            boolean deleted = outputFile.delete();
            if (!deleted) {
                return false;
            }
        }

        File parentFolder = outputFile.getParentFile();
        if (!parentFolder.exists()) {
            try {
                boolean created = parentFolder.mkdirs();
                if (!created) {
                    log.error("createFolder|error|" + parentFolder.getAbsolutePath());
                    return false;
                } else {
                    log.error("createFolder|succ|" + parentFolder.getAbsolutePath());
                }
            } catch (Exception e) {
                log.error("createFolder|fatal|" + parentFolder.getAbsolutePath() + "e=" + e.getMessage(), e);
                return false;
            }
        }

        try {
            boolean created = outputFile.createNewFile();
            if (!created) {
                log.error("createNewFile|error|" + outputFile.getAbsolutePath());
                return false;
            }
        } catch (IOException e) {
            log.error("createNewFile|fatal|" + outputFile.getAbsolutePath() + "e=" + e.getMessage(), e);
            return false;
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter(outputFile);
            writer.write(outputCont);
            writer.flush();
            return true;
        } catch (IOException e) {
            log.error("writeFile|fatal|" + outputFile.getAbsolutePath() + "e=" + e.getMessage(), e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error("closeWriter|fatal|" + outputFile.getAbsolutePath() + "e=" + e.getMessage(), e);
                }
            }
        }
        return false;
    }

    public static String uploadOSS(File file) {
        String ossPath = null;
        try {
            ossPath = OssUtils.uploadFile(file, AliyunConsts.OSS_MENU_PATH);
        } catch (FileNotFoundException e) {
            log.error("uploadFileToOSS|fatal|e=" + e.getMessage());
        }
        return ossPath;
    }

    public static String calcMD5Hex(File file) {
        String md5AsHex = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            md5AsHex = DigestUtils.md5DigestAsHex(fileInputStream);
        } catch (IOException e) {
            log.error("calcMD5Hex|fatal|e=" + e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    log.error("closeFileInputStream|fatal|e=" + e.getMessage());
                }
            }
        }
        return md5AsHex;
    }

    public static int calcRandom(int min, int max) {
        Random random = new Random(System.currentTimeMillis());
        int randomNumber = random.nextInt(max - min + 1) + min;
        return randomNumber;
    }
}
