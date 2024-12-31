package com.langtuo.teamachine.api.service.record;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.record.InvalidActRecordDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface InvalidActRecordMgtService {
    /**
     *
     * @param tenantCode
     * @param idempotentMark
     * @return
     */
    TeaMachineResult<InvalidActRecordDTO> get(String tenantCode, String idempotentMark);

    /**
     *
     * @return
     */
    TeaMachineResult<PageDTO<InvalidActRecordDTO>> search(String tenantCode, String shopGroupCode, String shopCode,
            int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @param idempotentMark
     * @return
     */
    TeaMachineResult<Void> delete(String tenantCode, String idempotentMark);
}
