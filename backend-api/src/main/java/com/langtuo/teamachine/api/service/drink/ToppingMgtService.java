package com.langtuo.teamachine.api.service.drink;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.ToppingDTO;
import com.langtuo.teamachine.api.request.drink.ToppingPutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface ToppingMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<ToppingDTO> getByToppingCode(String tenantCode, String toppingCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<ToppingDTO>> search(String tenantCode, String toppingCode, String toppingName,
            int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<ToppingDTO>> list(String tenantCode);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(ToppingPutRequest request);

    /**
     *
     * @param tenantCode
     * @param toppingCode
     * @return
     */
    TeaMachineResult<Void> deleteByToppingCode(String tenantCode, String toppingCode);
}
