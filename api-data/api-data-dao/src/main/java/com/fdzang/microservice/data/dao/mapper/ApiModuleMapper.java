package com.fdzang.microservice.data.dao.mapper;

import com.fdzang.microservice.data.dao.domain.ApiModuleDO;
import com.fdzang.microservice.data.dao.domain.ApiModuleDOExample;
import java.util.List;

public interface ApiModuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table api_module
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table api_module
     *
     * @mbg.generated
     */
    int insert(ApiModuleDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table api_module
     *
     * @mbg.generated
     */
    int insertSelective(ApiModuleDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table api_module
     *
     * @mbg.generated
     */
    List<ApiModuleDO> selectByExample(ApiModuleDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table api_module
     *
     * @mbg.generated
     */
    ApiModuleDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table api_module
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ApiModuleDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table api_module
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ApiModuleDO record);
}