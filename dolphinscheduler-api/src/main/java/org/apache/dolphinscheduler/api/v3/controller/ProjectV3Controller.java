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
import org.apache.dolphinscheduler.api.utils.PaginationUtils;
import org.apache.dolphinscheduler.api.utils.QueryResult;
import org.apache.dolphinscheduler.api.v3.service.ProjectV3Service;
import org.apache.dolphinscheduler.dao.entity.Project;
import org.apache.dolphinscheduler.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


/**
 * project controller
 */
@RestController
@RequestMapping("/v3/projects")
@Slf4j
public class ProjectV3Controller extends BaseController {

    @Autowired
    private ProjectV3Service projectV3Service;

    /**
     * query project details by code
     *
     * @param code      project code
     * @return project detail information
     */
    @GetMapping(value = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public Project get(
            @PathVariable("code") long code
    ) {
        return projectV3Service.queryProject(code);
    }

    /**
     * query project list paging
     *
     * @param searchVal  search value
     * @param nextToken  next token
     * @param maxResults max results
     * @return project list which the login user have permission to see
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public QueryResult<Project> list(
            Authentication authentication,
            @RequestParam(value = "searchVal", required = false) String searchVal,
            @RequestParam(value = "nextToken", required = false) String nextToken,
            @RequestParam(value = "maxResults", required = false, defaultValue = "10") Integer maxResults
    ) {
        int userId = ((User) authentication.getPrincipal()).getId();
        int offset = nextToken != null ? Integer.parseInt(PaginationUtils.decodeNextToken(nextToken)) : 0;
        return projectV3Service.listProjects(userId, offset, maxResults, searchVal);
    }
}
