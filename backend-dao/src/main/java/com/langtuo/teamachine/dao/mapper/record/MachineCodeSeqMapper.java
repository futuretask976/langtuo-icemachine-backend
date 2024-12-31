package com.langtuo.teamachine.dao.mapper.record;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MachineCodeSeqMapper {
    /**
     *
     * @return
     */
    public long getNextSeqValue();
}
