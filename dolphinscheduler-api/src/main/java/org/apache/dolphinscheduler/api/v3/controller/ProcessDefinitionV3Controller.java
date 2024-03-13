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
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.exceptions.ServiceException;
import org.apache.dolphinscheduler.api.service.ProjectService;
import org.apache.dolphinscheduler.api.utils.PaginationUtils;
import org.apache.dolphinscheduler.api.utils.QueryResult;
import org.apache.dolphinscheduler.api.v3.service.ProcessDefinitionV3Service;
import org.apache.dolphinscheduler.api.v3.service.ProjectV3Service;
import org.apache.dolphinscheduler.dao.entity.DagData;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.dolphinscheduler.dao.entity.Project;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.plugin.task.api.utils.ParameterUtils;
import org.apache.dolphinscheduler.service.process.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


/**
 * process definition controller
 */
@RestController
@RequestMapping("/v3/projects/{projectCode}/process-definition")
@Slf4j
public class ProcessDefinitionV3Controller {

    @Autowired
    private ProcessDefinitionV3Service processDefinitionService;

    @Autowired
    private ProjectV3Service projectService;

    @Autowired
    private ProcessService processService;

    /**
     * query detail of process definition by code
     *
     * @param projectCode project code
     * @param code        process definition code
     * @return process definition detail
     */
    @GetMapping(value = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public DagData queryProcessDefinitionByCode(
            @PathVariable long projectCode,
            @PathVariable(value = "code") long code) {
        Project project = projectService.queryProject(projectCode);

        if (project == null) {
            throw new ServiceException(Status.PROJECT_NOT_FOUND);
        }

        ProcessDefinition processDefinition = processDefinitionService.queryProcessDefinitionByCode(projectCode, code);

        if (processDefinition == null) {
            throw new ServiceException(Status.PROCESS_DEFINE_NOT_EXIST);
        }

        return processService.genDagData(processDefinition);
    }

    /**
     * query process definition list paging
     *
     * @param projectCode project code
     * @param searchVal   search value
     * @return process definition page
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public QueryResult<ProcessDefinition> queryProcessDefinitionListPaging(
            Authentication authentication,
            @PathVariable long projectCode,
            @RequestParam(value = "searchVal", required = false) String searchVal,
            @RequestParam(value = "nextToken", required = false) String nextToken,
            @RequestParam(value = "maxResults", required = false, defaultValue = "10") Integer maxResults) {
        searchVal = ParameterUtils.handleEscapes(searchVal);
        int userId = ((User) authentication.getPrincipal()).getId();
        int offset = nextToken != null ? Integer.parseInt(PaginationUtils.decodeNextToken(nextToken)) : 0;

        return processDefinitionService.listProcessDefinitions(
                userId,
                projectCode,
                searchVal,
                offset,
                maxResults
        );
    }
}
