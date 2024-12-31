package com.langtuo.teamachine.dao.mapper.user;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.user.AdminPO;
import com.langtuo.teamachine.dao.query.user.AdminQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    /**
     *
     * @param tenantCode
     * @param loginName
     * @return
     */
    AdminPO selectOne(@Param("tenantCode") String tenantCode, @Param("loginName") String loginName);

    /**
     *
     * @return
     */
    List<AdminPO> selectList(@Param("tenantCode") String tenantCode);

    /**
     *
     * @param query
     * @return
     */
    List<AdminPO> search(AdminQuery query);

    /**
     *
     * @param po
     * @return
     */
    int insert(AdminPO po);

    /**
     *
     * @param po
     * @return
     */
    int update(AdminPO po);

    /**
     *
     * @param tenantCode
     * @param loginName
     * @param loginPass
     * @return
     */
    int updatePassword(@Param("tenantCode") String tenantCode, @Param("loginName") String loginName,
            @Param("loginPass") String loginPass);

    /**
     *
     * @param tenantCode
     * @param loginName
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("loginName") String loginName);

    /**
     *
     * @param tenantCode
     * @param roleCode
     * @return
     */
    int countByRoleCode(@Param("tenantCode") String tenantCode, @Param("roleCode") String roleCode);

    /**
     *
     * @param tenantCode
     * @param orgName
     * @return
     */
    int countByOrgName(@Param("tenantCode") String tenantCode, @Param("orgName") String orgName);
}
