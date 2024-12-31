package com.langtuo.teamachine.api.service.record;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.record.CleanActRecordDTO;
import com.langtuo.teamachine.api.result.IceMachineResult;

public interface ActRecordMgtService {
    /**
     *
     * @param tenantCode
     * @param idempotentMark
     * @return
     */
    IceMachineResult<CleanActRecordDTO> get(String tenantCode, String idempotentMark);

    /**
     *
     * @return
     */
    IceMachineResult<PageDTO<CleanActRecordDTO>> search(String tenantCode, String shopGroupCode, String shopCode,
                                                        int pageNum, int pageSize);

    /**
     *
     * @param tenantCode
     * @param idempotentMark
     * @return
     */
    IceMachineResult<Void> delete(String tenantCode, String idempotentMark);
}
