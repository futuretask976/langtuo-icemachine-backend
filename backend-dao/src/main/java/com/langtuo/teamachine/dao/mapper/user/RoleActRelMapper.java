package com.langtuo.teamachine.dao.mapper.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleActRelMapper {
    /**
     *
     * @param tenantCode
     * @param roleCode
     * @return
     */
    RoleActRelPO selectOne(@Param("tenantCode") String tenantCode, @Param("roleCode") String roleCode,
            @Param("permitActCode") String permitActCode);

    /**
     *
     * @return
     */
    List<RoleActRelPO> selectList(@Param("tenantCode") String tenantCode, @Param("roleCode") String roleCode);

    /**
     *
     * @param po
     * @return
     */
    int insert(RoleActRelPO po);

    /**
     *
     * @param po
     * @return
     */
    int update(RoleActRelPO po);

    /**
     *
     * @param tenantCode
     * @param roleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("roleCode") String roleCode);
}
