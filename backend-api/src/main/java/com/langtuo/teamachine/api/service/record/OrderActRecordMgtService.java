package com.langtuo.teamachine.api.service.record;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.record.OrderActRecordDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface OrderActRecordMgtService {
    /**
     *
     * @param tenantCode
     * @param idempotentMark
     * @return
     */
    TeaMachineResult<OrderActRecordDTO> get(String tenantCode, String shopGroupCode, String idempotentMark);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<OrderActRecordDTO>> search(String tenantCode, String shopGroupCode, String shopCode,
            int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @param idempotentMark
     * @return
     */
    TeaMachineResult<Void> delete(String tenantCode, String shopGroupCode, String idempotentMark);
}
