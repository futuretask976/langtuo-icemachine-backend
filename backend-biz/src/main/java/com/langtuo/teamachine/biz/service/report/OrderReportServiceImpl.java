package com.langtuo.teamachine.biz.service.report;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.report.OrderReportByDayDTO;
import com.langtuo.teamachine.api.model.report.OrderSpecItemReportByDayDTO;
import com.langtuo.teamachine.api.model.report.OrderTeaReportByDayDTO;
import com.langtuo.teamachine.api.model.report.OrderToppingReportByDayDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.report.OrderReportService;
import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.biz.manager.ShopGroupManager;
import com.langtuo.teamachine.dao.accessor.report.OrderReportAccessor;
import com.langtuo.teamachine.dao.accessor.report.OrderSpecItemReportAccessor;
import com.langtuo.teamachine.dao.accessor.report.OrderTeaReportAccessor;
import com.langtuo.teamachine.dao.accessor.report.OrderToppingReportAccessor;
import com.langtuo.teamachine.dao.po.report.OrderReportPO;
import com.langtuo.teamachine.dao.po.report.OrderSpecItemReportPO;
import com.langtuo.teamachine.dao.po.report.OrderTeaReportPO;
import com.langtuo.teamachine.dao.po.report.OrderToppingReportPO;;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class OrderReportServiceImpl implements OrderReportService {
    @Resource
    private ShopGroupManager shopGroupManager;

    @Resource
    private OrderReportAccessor orderReportAccessor;

    @Resource
    private OrderSpecItemReportAccessor orderSpecItemReportAccessor;

    @Resource
    private OrderTeaReportAccessor orderTeaReportAccessor;

    @Resource
    private OrderToppingReportAccessor orderToppingReportAccessor;

    /**
     * 该方法尽量不要添加事务，避免订单记录插入失败
     * @param tenantCode
     * @param orderCreatedDay
     * @return
     */
    @Override
    public TeaMachineResult<Void> calc(String tenantCode, String orderCreatedDay) {
        if (StringUtils.isBlank(tenantCode) || StringUtils.isBlank(orderCreatedDay)) {
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.BIZ_ERR_ILLEGAL_ARGUMENT));
        }

        orderReportAccessor.delete(tenantCode, orderCreatedDay);
        List<OrderReportPO> orderReportPOList = orderReportAccessor.calcByOrderCreatedDay(tenantCode, orderCreatedDay);
        if (!CollectionUtils.isEmpty(orderReportPOList)) {
            for (OrderReportPO po : orderReportPOList) {
                int inserted4OrderReport = orderReportAccessor.insert(po);
            }
        }

        orderTeaReportAccessor.delete(tenantCode, orderCreatedDay);
        List<OrderTeaReportPO> teaReportByShopPOList = orderTeaReportAccessor.calcByOrderCreatedDay(
                tenantCode, orderCreatedDay);
        if (!CollectionUtils.isEmpty(teaReportByShopPOList)) {
            for (OrderTeaReportPO po : teaReportByShopPOList) {
                int inserted = orderTeaReportAccessor.insert(po);
            }
        }

        orderSpecItemReportAccessor.delete(tenantCode, orderCreatedDay);
        List<OrderSpecItemReportPO> specItemReportByShopPOList = orderSpecItemReportAccessor.calcByOrderCreatedDay(
                tenantCode, orderCreatedDay);
        if (!CollectionUtils.isEmpty(specItemReportByShopPOList)) {
            for (OrderSpecItemReportPO po : specItemReportByShopPOList) {
                int inserted = orderSpecItemReportAccessor.insert(po);
            }
        }

        orderToppingReportAccessor.delete(tenantCode, orderCreatedDay);
        List<OrderToppingReportPO> toppingReportByShopPOList = orderToppingReportAccessor.calcByOrderCreatedDay(
                tenantCode, orderCreatedDay);
        if (!CollectionUtils.isEmpty(toppingReportByShopPOList)) {
            for (OrderToppingReportPO po : toppingReportByShopPOList) {
                int inserted = orderToppingReportAccessor.insert(po);
            }
        }

        return TeaMachineResult.success();
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<OrderReportByDayDTO>> searchOrderReport(String tenantCode, String orderCreatedDay,
            int pageNum, int pageSize) {
        pageNum = pageNum < CommonConsts.MIN_PAGE_NUM ? CommonConsts.MIN_PAGE_NUM : pageNum;
        pageSize = pageSize < CommonConsts.MIN_PAGE_SIZE ? CommonConsts.MIN_PAGE_SIZE : pageSize;

        List orderCreatedDayList = null;
        if (!StringUtils.isBlank(orderCreatedDay)) {
            orderCreatedDayList = Lists.newArrayList(orderCreatedDay);
        }

        try {
            PageInfo<OrderReportPO> pageInfo = orderReportAccessor.search(tenantCode, orderCreatedDayList,
                    pageNum, pageSize);
            if (pageInfo == null) {
                return TeaMachineResult.success(new PageDTO<>(null, 0, pageNum, pageSize));
            }
            return TeaMachineResult.success(new PageDTO<>(convertToOrderReportByDayDTO(pageInfo.getList()),
                    pageInfo.getTotal(), pageNum, pageSize));
        } catch (Exception e) {
            log.error("searchAmtReport|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<OrderTeaReportByDayDTO>> searchTeaReport(String tenantCode, String orderCreatedDay,
            String shopGroupCode, String shopCode, int pageNum, int pageSize) {
        List orderCreatedDayList = null;
        if (!StringUtils.isBlank(orderCreatedDay)) {
            orderCreatedDayList = Lists.newArrayList(orderCreatedDay);
        }

        try {
            PageInfo<OrderTeaReportPO> pageInfo = null;
            if (!StringUtils.isBlank(shopCode)) {
                pageInfo = orderTeaReportAccessor.searchByShopCode(tenantCode, orderCreatedDayList,
                        Lists.newArrayList(shopCode), pageNum, pageSize);
            } else if (!StringUtils.isBlank(shopGroupCode)) {
                pageInfo = orderTeaReportAccessor.searchByShopGroupCode(tenantCode, orderCreatedDayList,
                        Lists.newArrayList(shopGroupCode), pageNum, pageSize);
            } else {
                List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByLoginSession(tenantCode);
                pageInfo = orderTeaReportAccessor.searchByShopGroupCode(tenantCode, orderCreatedDayList,
                        shopGroupCodeList, pageNum, pageSize);
            }

            if (pageInfo == null) {
                return TeaMachineResult.success(new PageDTO<>(null, 0, pageNum, pageSize));
            }
            return TeaMachineResult.success(new PageDTO<>(convertToOrderTeaReportByDayDTO(pageInfo.getList()),
                    pageInfo.getTotal(), pageNum, pageSize));
        } catch (Exception e) {
            log.error("searchTeaReport|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<OrderSpecItemReportByDayDTO>> searchSpecItemReport(String tenantCode,
            String orderCreatedDay, String shopGroupCode, String shopCode, int pageNum, int pageSize) {
        List orderCreatedDayList = null;
        if (!StringUtils.isBlank(orderCreatedDay)) {
            orderCreatedDayList = Lists.newArrayList(orderCreatedDay);
        }

        try {
            PageInfo<OrderSpecItemReportPO> pageInfo = null;
            if (!StringUtils.isBlank(shopCode)) {
                pageInfo = orderSpecItemReportAccessor.searchByShopCode(tenantCode, orderCreatedDayList,
                        Lists.newArrayList(shopCode), pageNum, pageSize);
            } else if (!StringUtils.isBlank(shopGroupCode)) {
                pageInfo = orderSpecItemReportAccessor.searchByShopGroupCode(tenantCode, orderCreatedDayList,
                        Lists.newArrayList(shopGroupCode), pageNum, pageSize);
            } else {
                List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByLoginSession(tenantCode);
                pageInfo = orderSpecItemReportAccessor.searchByShopGroupCode(tenantCode, orderCreatedDayList,
                        shopGroupCodeList, pageNum, pageSize);
            }

            if (pageInfo == null) {
                return TeaMachineResult.success(new PageDTO<>(null, 0, pageNum, pageSize));
            }
            return TeaMachineResult.success(new PageDTO<>(convertToOrderSpecItemReportByDayDTO(pageInfo.getList()),
                    pageInfo.getTotal(), pageNum, pageSize));
        } catch (Exception e) {
            log.error("searchSpecItemReport|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TeaMachineResult<PageDTO<OrderToppingReportByDayDTO>> searchToppingReport(String tenantCode,
            String orderCreatedDay, String shopGroupCode, String shopCode, int pageNum, int pageSize) {
        List orderCreatedDayList = null;
        if (!StringUtils.isBlank(orderCreatedDay)) {
            orderCreatedDayList = Lists.newArrayList(orderCreatedDay);
        }

        try {
            PageInfo<OrderToppingReportPO> pageInfo = null;
            if (!StringUtils.isBlank(shopCode)) {
                pageInfo = orderToppingReportAccessor.searchByShopCode(tenantCode, orderCreatedDayList,
                        Lists.newArrayList(shopCode), pageNum, pageSize);
            } else if (!StringUtils.isBlank(shopGroupCode)) {
                pageInfo = orderToppingReportAccessor.searchByShopGroupCode(tenantCode, orderCreatedDayList,
                        Lists.newArrayList(shopGroupCode), pageNum, pageSize);
            } else {
                List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByLoginSession(tenantCode);
                pageInfo = orderToppingReportAccessor.searchByShopGroupCode(tenantCode, orderCreatedDayList,
                        shopGroupCodeList, pageNum, pageSize);
            }

            if (pageInfo == null) {
                return TeaMachineResult.success(new PageDTO<>(null, 0, pageNum, pageSize));
            }
            return TeaMachineResult.success(new PageDTO<>(convertToOrderToppingReportByDayDTO(pageInfo.getList()),
                    pageInfo.getTotal(), pageNum, pageSize));
        } catch (Exception e) {
            log.error("searchToppingReport|fatal|e=" + e.getMessage(), e);
            return TeaMachineResult.error(LocaleUtils.getErrorMsgDTO(ErrorCodeEnum.DB_ERR_SELECT_FAIL));
        }
    }

    private List<OrderReportByDayDTO> convertToOrderReportByDayDTO(List<OrderReportPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        Map<String, OrderReportByDayDTO> mapByDay = Maps.newHashMap();
        for (OrderReportPO po : poList) {
            OrderReportByDayDTO exist = mapByDay.get(po.getOrderCreatedDay());
            if (exist == null) {
                exist = new OrderReportByDayDTO();
                exist.setOrderCreatedDay(po.getOrderCreatedDay());
                mapByDay.put(po.getOrderCreatedDay(), exist);
            }
            exist.setAmount(exist.getAmount() + po.getAmount());
        }

        List<OrderReportByDayDTO> resultList = Lists.newArrayList();
        for (Map.Entry<String, OrderReportByDayDTO> entry : mapByDay.entrySet()) {
            resultList.add(entry.getValue());
        }
        resultList.sort((a, b) -> b.getOrderCreatedDay().compareTo(a.getOrderCreatedDay()));
        return resultList;
    }

    private List<OrderTeaReportByDayDTO> convertToOrderTeaReportByDayDTO(List<OrderTeaReportPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        Map<String, Map<String, OrderTeaReportByDayDTO>> mapByDay = Maps.newHashMap();
        for (OrderTeaReportPO po : poList) {
            Map<String, OrderTeaReportByDayDTO> mapByTopping = mapByDay.get(po.getOrderCreatedDay());
            if (mapByTopping == null) {
                mapByTopping = Maps.newHashMap();
                mapByDay.put(po.getOrderCreatedDay(), mapByTopping);
            }

            OrderTeaReportByDayDTO exist = mapByTopping.get(po.getTeaCode());
            if (exist == null) {
                exist = new OrderTeaReportByDayDTO();
                exist.setOrderCreatedDay(po.getOrderCreatedDay());
                exist.setTeaCode(po.getTeaCode());
                mapByTopping.put(po.getTeaCode(), exist);
            }
            exist.setAmount(exist.getAmount() + po.getAmount());
        }

        List<OrderTeaReportByDayDTO> resultList = Lists.newArrayList();
        for (Map.Entry<String, Map<String, OrderTeaReportByDayDTO>> entryByDay : mapByDay.entrySet()) {
            for (Map.Entry<String, OrderTeaReportByDayDTO> entryByTopping : entryByDay.getValue().entrySet()) {
                resultList.add(entryByTopping.getValue());
            }
        }
        resultList.sort((a, b) -> b.getOrderCreatedDay().compareTo(a.getOrderCreatedDay()));
        return resultList;
    }

    private List<OrderSpecItemReportByDayDTO> convertToOrderSpecItemReportByDayDTO(
            List<OrderSpecItemReportPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        Map<String, Map<String, OrderSpecItemReportByDayDTO>> mapByDay = Maps.newHashMap();
        for (OrderSpecItemReportPO po : poList) {
            Map<String, OrderSpecItemReportByDayDTO> mapByTopping = mapByDay.get(po.getOrderCreatedDay());
            if (mapByTopping == null) {
                mapByTopping = Maps.newHashMap();
                mapByDay.put(po.getOrderCreatedDay(), mapByTopping);
            }

            OrderSpecItemReportByDayDTO exist = mapByTopping.get(po.getSpecItemCode());
            if (exist == null) {
                exist = new OrderSpecItemReportByDayDTO();
                exist.setOrderCreatedDay(po.getOrderCreatedDay());
                exist.setSpecItemCode(po.getSpecItemCode());
                mapByTopping.put(po.getSpecItemCode(), exist);
            }
            exist.setAmount(exist.getAmount() + po.getAmount());
        }

        List<OrderSpecItemReportByDayDTO> resultList = Lists.newArrayList();
        for (Map.Entry<String, Map<String, OrderSpecItemReportByDayDTO>> entryByDay : mapByDay.entrySet()) {
            for (Map.Entry<String, OrderSpecItemReportByDayDTO> entryByTopping : entryByDay.getValue().entrySet()) {
                resultList.add(entryByTopping.getValue());
            }
        }
        resultList.sort((a, b) -> b.getOrderCreatedDay().compareTo(a.getOrderCreatedDay()));
        return resultList;
    }

    private List<OrderToppingReportByDayDTO> convertToOrderToppingReportByDayDTO(
            List<OrderToppingReportPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        Map<String, Map<String, OrderToppingReportByDayDTO>> mapByDay = Maps.newHashMap();
        for (OrderToppingReportPO po : poList) {
            Map<String, OrderToppingReportByDayDTO> mapByTopping = mapByDay.get(po.getOrderCreatedDay());
            if (mapByTopping == null) {
                mapByTopping = Maps.newHashMap();
                mapByDay.put(po.getOrderCreatedDay(), mapByTopping);
            }

            OrderToppingReportByDayDTO exist = mapByTopping.get(po.getToppingCode());
            if (exist == null) {
                exist = new OrderToppingReportByDayDTO();
                exist.setOrderCreatedDay(po.getOrderCreatedDay());
                exist.setToppingCode(po.getToppingCode());
                mapByTopping.put(po.getToppingCode(), exist);
            }
            exist.setAmount(exist.getAmount() + po.getAmount());
        }

        List<OrderToppingReportByDayDTO> resultList = Lists.newArrayList();
        for (Map.Entry<String, Map<String, OrderToppingReportByDayDTO>> entryByDay : mapByDay.entrySet()) {
            for (Map.Entry<String, OrderToppingReportByDayDTO> entryByTopping : entryByDay.getValue().entrySet()) {
                resultList.add(entryByTopping.getValue());
            }
        }
        resultList.sort((a, b) -> b.getOrderCreatedDay().compareTo(a.getOrderCreatedDay()));
        return resultList;
    }
}
