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

package org.apache.dolphinscheduler.api.v3.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.service.impl.BaseServiceImpl;
import org.apache.dolphinscheduler.api.utils.PaginationUtils;
import org.apache.dolphinscheduler.api.utils.QueryResult;
import org.apache.dolphinscheduler.api.v3.service.ProcessInstanceV3Service;
import org.apache.dolphinscheduler.common.enums.WorkflowExecutionStatus;
import org.apache.dolphinscheduler.dao.entity.*;
import org.apache.dolphinscheduler.dao.mapper.v3.ProcessInstanceV3Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * process instance service impl
 */
@Service
@Slf4j
public class ProcessInstanceV3ServiceImpl extends BaseServiceImpl implements ProcessInstanceV3Service {


    @Autowired
    ProcessInstanceV3Mapper processInstanceMapper;


    /**
     * query process instance by id
     *
     * @param processInstanceId   process instance id
     * @return process instance detail
     */
    @Override
    public ProcessInstance queryProcessInstanceById(Integer processInstanceId) {
        return processInstanceMapper.queryProcessInstanceById(processInstanceId);
    }

    /**
     * paging query process instance list, filtering according to project, process definition, time range, keyword, process status
     *
     * @param processDefineCode process definition code
     * @param offset            page number
     * @param maxResults        page size
     * @param searchVal         search value
     * @param stateType         state type
     * @param startDate         start time
     * @param endDate           end time
     * @return process instance list
     */
    @Override
    public QueryResult<ProcessInstance> queryProcessInstanceList(
            long projectCode,
            Long processDefineCode,
            String startDate,
            String endDate,
            String searchVal,
            WorkflowExecutionStatus stateType,
            int offset,
            int maxResults
    ) {
        QueryResult<ProcessInstance> processInstanceQueryResult = new QueryResult<>();

        Date start = checkAndParseDateParameters(startDate);
        Date end = checkAndParseDateParameters(endDate);

        int[] states = stateType != null ? new int[]{stateType.getCode()} : null;

        int count = processInstanceMapper.countProcessInstances(
                projectCode,
                processDefineCode,
                searchVal,
                states,
                start,
                end
        );

        List<ProcessInstance> processInstances = processInstanceMapper.listProcessInstances(
                projectCode,
                processDefineCode,
                searchVal,
                states,
                start,
                end,
                offset,
                maxResults
        );

        if (!processInstances.isEmpty()) {
            String nextToken = PaginationUtils.encodeNextToken(
                    processInstances.get(processInstances.size() - 1).getId().toString()
            );
            processInstanceQueryResult.setNextToken(nextToken);
        }

        processInstanceQueryResult.setTotalSize(count);
        processInstanceQueryResult.setData(processInstances);


        return processInstanceQueryResult;
    }
}
