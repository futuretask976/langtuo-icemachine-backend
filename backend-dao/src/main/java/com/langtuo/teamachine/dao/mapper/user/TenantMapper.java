package com.langtuo.teamachine.dao.mapper.user;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.user.TenantPO;
import com.langtuo.teamachine.dao.query.user.TenantQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TenantMapper {
    /**
     *
     * @param tenantCode
     * @return
     */
    TenantPO selectOne(@Param("tenantCode") String tenantCode);

    /**
     *
     * @return
     */
    List<TenantPO> selectList();

    /**
     *
     * @return
     */
    List<TenantPO> search(TenantQuery tenantQuery);

    /**
     *
     * @param po
     * @return
     */
    int insert(TenantPO po);

    /**
     *
     * @param po
     * @return
     */
    int update(TenantPO po);

    /**
     *
     * @param tenantCode
     * @return
     */
    int delete(String tenantCode);
}
