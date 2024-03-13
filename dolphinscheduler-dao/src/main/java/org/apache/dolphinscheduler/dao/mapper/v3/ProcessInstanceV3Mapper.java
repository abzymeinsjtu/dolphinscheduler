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
import org.apache.dolphinscheduler.dao.entity.ProcessInstance;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * process instance mapper interface
 */
public interface ProcessInstanceV3Mapper extends BaseMapper<ProcessInstance> {

    /**
     * query process instance detail info by id
     *
     * @param processId processId
     * @return process instance
     */
    ProcessInstance queryProcessInstanceById(@Param("processId") int processId);

    /**
     * process instance page
     *
     * @param searchVal             searchVal
     * @param statusArray           statusArray
     * @param endTime               endTime
     * @return process instance page
     */
    List<ProcessInstance> listProcessInstances(
            @Param("projectCode") long projectCode,
            @Param("processDefinitionCode") Long processDefinitionCode,
            @Param("searchVal") String searchVal,
            @Param("states") int[] statusArray,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime,
            @Param("offset") int offset,
            @Param("maxResults") int maxResults);

    Integer countProcessInstances(
            @Param("projectCode") long projectCode,
            @Param("processDefinitionCode") Long processDefinitionCode,
            @Param("searchVal") String searchVal,
            @Param("states") int[] statusArray,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime);
}
