package com.langtuo.teamachine.api.service.drink;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.drink.ToppingTypeDTO;
import com.langtuo.teamachine.api.request.drink.ToppingTypePutRequest;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface ToppingTypeMgtService {
    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<ToppingTypeDTO> getByToppingTypeCode(String tenantCode, String toppingTypeCode);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<ToppingTypeDTO>> search(String tenantCode, String toppingTypeCode, String toppingTypeName,
            int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @return
     */
    TeaMachineResult<List<ToppingTypeDTO>> list(String tenantCode);

    /**
     *
     * @param request
     * @return
     */
    TeaMachineResult<Void> put(ToppingTypePutRequest request);

    /**
     *
     * @param tenantCode
     * @param toppingTypeCode
     * @return
     */
    TeaMachineResult<Void> deleteByToppingTypeCode(String tenantCode, String toppingTypeCode);
}
