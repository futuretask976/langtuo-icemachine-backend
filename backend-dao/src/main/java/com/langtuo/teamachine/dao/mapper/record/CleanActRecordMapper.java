package com.langtuo.teamachine.dao.mapper.record;

import com.langtuo.teamachine.dao.query.record.CleanActRecordQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CleanActRecordMapper {
    /**
     *
     * @param tenantCode
     * @param idempotentMark
     * @return
     */
    CleanActRecordPO selectOne(@Param("tenantCode") String tenantCode,
            @Param("idempotentMark") String idempotentMark);

    /**
     *
     * @return
     */
    List<CleanActRecordPO> search(CleanActRecordQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(CleanActRecordPO po);

    /**
     *
     * @param tenantCode
     * @param idempotentMark
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("idempotentMark") String idempotentMark);
}
