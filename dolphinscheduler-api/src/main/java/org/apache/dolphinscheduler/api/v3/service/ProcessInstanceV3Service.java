
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

package org.apache.dolphinscheduler.api.v3.service;

import org.apache.dolphinscheduler.api.utils.QueryResult;
import org.apache.dolphinscheduler.common.enums.WorkflowExecutionStatus;
import org.apache.dolphinscheduler.dao.entity.ProcessInstance;

import java.util.Date;

/**
 * process instance service
 */

public interface ProcessInstanceV3Service {

    /**
     * process instance by id
     *
     * @param processInstanceId
     */
    ProcessInstance queryProcessInstanceById(Integer processInstanceId);


    /**
     * paging query process instance list, filtering according to project, process definition, time range, keyword, process status
     *
     * @param processDefineCode process definition code
     * @param searchVal         search value
     * @param stateType         state type
     * @param startDate         start time
     * @param endDate           end time
     * @param offset            offset
     * @param maxResults        max results
     * @param stateType         state type
     * @return process instance list
     */
    QueryResult<ProcessInstance> queryProcessInstanceList(
            long projectCode,
            Long processDefineCode,
            String startDate,
            String endDate,
            String searchVal,
            WorkflowExecutionStatus stateType,
            int offset,
            int maxResults);
}
