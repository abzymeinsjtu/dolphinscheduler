/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.dao.mapper.v3;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.dolphinscheduler.dao.entity.DependentSimplifyDefinition;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.dolphinscheduler.dao.model.WorkflowDefinitionCountDto;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * process definition mapper interface
 */
public interface ProcessDefinitionV3Mapper extends BaseMapper<ProcessDefinition> {

    /**
     * query process definition by code
     *
     * @param code code
     * @return process definition
     */
    ProcessDefinition queryProcessDefinitionByCode(@Param("code") long code);

    /**
     * process definition page
     *
     * @param searchVal   searchVal
     * @param userId      userId
     * @param projectCode projectCode
     * @return process definition IPage
     */
    List<ProcessDefinition> listProcessDefinitions(
            @Param("searchVal") String searchVal,
            @Param("userId") int userId,
            @Param("projectCode") long projectCode,
            @Param("offset") int offset,
            @Param("maxResults") int maxResults
    );


    Integer countProcessDefinitions(
            @Param("searchVal") String searchVal,
            @Param("userId") int userId,
            @Param("projectCode") long projectCode
    );
}
