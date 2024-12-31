package com.langtuo.teamachine.dao.mapper.record;

import com.langtuo.teamachine.dao.po.record.ActRecordPO;
import com.langtuo.teamachine.dao.query.record.DrainActRecordQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActRecordMapper {
    /**
     *
     * @param tenantCode
     * @param idempotentMark
     * @return
     */
    ActRecordPO selectOne(@Param("tenantCode") String tenantCode,
                          @Param("idempotentMark") String idempotentMark);

    /**
     *
     * @return
     */
    List<ActRecordPO> search(DrainActRecordQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(ActRecordPO po);

    /**
     *
     * @param tenantCode
     * @param idempotentMark
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("idempotentMark") String idempotentMark);
}
