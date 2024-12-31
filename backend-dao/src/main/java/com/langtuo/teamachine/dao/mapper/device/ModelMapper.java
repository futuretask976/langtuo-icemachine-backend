package com.langtuo.teamachine.dao.mapper.device;

import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import com.langtuo.teamachine.dao.po.device.ModelPO;
import com.langtuo.teamachine.dao.query.device.MachineModelQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ModelMapper {
    /**
     *
     * @param modelCode
     * @return
     */
    ModelPO selectOne(@Param("modelCode") String modelCode);

    /**
     *
     * @return
     */
    List<ModelPO> selectList();

    /**
     *
     * @return
     */
    List<ModelPO> search(MachineModelQuery machineModelQuery);

    /**
     *
     * @param po
     * @return
     */
    int insert(ModelPO po);

    /**
     *
     * @param po
     * @return
     */
    int update(ModelPO po);

    /**
     *
     * @param machineCode
     * @return
     */
    int delete(String machineCode);
}
