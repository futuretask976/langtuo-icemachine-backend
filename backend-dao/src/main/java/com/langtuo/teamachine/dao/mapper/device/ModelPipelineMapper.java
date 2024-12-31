package com.langtuo.teamachine.dao.mapper.device;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ModelPipelineMapper {
    /**
     *
     * @param modelCode
     * @param pipelineNum
     * @return
     */
    ModelPipelinePO selectOne(@Param("modelCode") String modelCode, @Param("pipelineNum") String pipelineNum);

    /**
     *
     * @return
     */
    List<ModelPipelinePO> selectList(String modelCode);

    /**
     *
     * @param po
     * @return
     */
    int insert(ModelPipelinePO po);

    /**
     *
     * @param po
     * @return
     */
    int update(ModelPipelinePO po);

    /**
     *
     * @param modelCode
     * @return
     */
    int delete(@Param("modelCode") String modelCode);
}
