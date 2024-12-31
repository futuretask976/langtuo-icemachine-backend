package com.langtuo.teamachine.dao.mapper.user;

import com.langtuo.teamachine.dao.query.user.AdminRoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
    /**
     *
     * @param tenantCode
     * @param roleCode
     * @return
     */
    RolePO selectOne(@Param("tenantCode") String tenantCode, @Param("roleCode") String roleCode,
            @Param("roleName") String roleName);

    /**
     *
     * @return
     */
    List<RolePO> selectList(@Param("tenantCode") String tenantCode);

    /**
     *
     * @param query
     * @return
     */
    List<RolePO> search(AdminRoleQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(RolePO po);

    /**
     *
     * @param po
     * @return
     */
    int update(RolePO po);

    /**
     *
     * @param tenantCode
     * @param roleCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("roleCode") String roleCode);
}
