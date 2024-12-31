package com.langtuo.teamachine.dao.mapper.user;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.user.OrgPO;
import com.langtuo.teamachine.dao.query.user.OrgStrucQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrgMapper {
    /**
     *
     * @param tenantCode
     * @param orgName
     * @return
     */
    OrgPO selectOne(@Param("tenantCode") String tenantCode, @Param("orgName") String orgName);

    /**
     *
     * @return
     */
    List<OrgPO> selectList(@Param("tenantCode") String tenantCode);

    /**
     *
     * @return
     */
    List<OrgPO> search(OrgStrucQuery orgStrucQuery);

    /**
     *
     * @param po
     * @return
     */
    int insert(OrgPO po);

    /**
     *
     * @param po
     * @return
     */
    int update(OrgPO po);

    /**
     *
     * @param tenantCode
     * @return
     */
    int delete(@Param("tenantCode") String tenantCode, @Param("orgName") String orgName);
}
