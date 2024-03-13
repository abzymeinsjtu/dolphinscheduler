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

package org.apache.dolphinscheduler.api.v3.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.controller.BaseController;
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.exceptions.ServiceException;
import org.apache.dolphinscheduler.api.utils.PaginationUtils;
import org.apache.dolphinscheduler.api.utils.QueryResult;
import org.apache.dolphinscheduler.api.v3.service.ProcessInstanceV3Service;
import org.apache.dolphinscheduler.api.v3.service.ProjectV3Service;
import org.apache.dolphinscheduler.common.enums.WorkflowExecutionStatus;
import org.apache.dolphinscheduler.dao.entity.ProcessInstance;
import org.apache.dolphinscheduler.dao.entity.Project;
import org.apache.dolphinscheduler.plugin.task.api.utils.ParameterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * process instance controller
 */
@RestController
@RequestMapping("/v3/projects/{projectCode}/process-instances")
@Slf4j
public class ProcessInstanceV3Controller extends BaseController {

    @Autowired
    private ProjectV3Service projectService;

    @Autowired
    private ProcessInstanceV3Service processInstanceService;

    /**
     * query task list by process instance id
     *
     * @param projectCode    project code
     * @param id             process instance id
     * @return task list for the process instance
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProcessInstance describeProcessInstance(@PathVariable long projectCode,
                                                   @PathVariable("id") Integer id) {
        Project project = projectService.queryProject(projectCode);

        if (project == null) {
            throw new ServiceException(Status.PROJECT_NOT_FOUND);
        }

        ProcessInstance processInstance = processInstanceService.queryProcessInstanceById(id);

        if (processInstance == null) {
            throw new ServiceException(Status.PROCESS_INSTANCE_NOT_EXIST);
        }

        return processInstance;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public QueryResult<ProcessInstance> listProcessInstance(
            @PathVariable long projectCode,
            @RequestParam(value = "processDefinitionCode", required = false) Long processDefinitionCode,
            @RequestParam(value = "searchVal", required = false) String searchVal,
            @RequestParam(value = "nextToken", required = false) String nextToken,
            @RequestParam(value = "startDate", required = false) String startTime,
            @RequestParam(value = "endDate", required = false) String endTime,
            @RequestParam(value = "stateType", required = false) WorkflowExecutionStatus stateType,
            @RequestParam(value = "maxResults", required = false, defaultValue = "10") Integer maxResults) {
        searchVal = ParameterUtils.handleEscapes(searchVal);
        int offset = nextToken != null ? Integer.parseInt(PaginationUtils.decodeNextToken(nextToken)) : 0;

        Project project = projectService.queryProject(projectCode);

        if (project == null) {
            throw new ServiceException(Status.PROJECT_NOT_FOUND);
        }

        return processInstanceService.queryProcessInstanceList(
                projectCode,
                processDefinitionCode,
                startTime,
                endTime,
                searchVal,
                stateType,
                offset,
                maxResults
        );

    }
}
