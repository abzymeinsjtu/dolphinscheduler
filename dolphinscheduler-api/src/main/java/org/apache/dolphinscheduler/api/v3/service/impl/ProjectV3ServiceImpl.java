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
import org.apache.dolphinscheduler.api.v3.service.ProjectV3Service;
import org.apache.dolphinscheduler.api.utils.PaginationUtils;
import org.apache.dolphinscheduler.api.utils.QueryResult;
import org.apache.dolphinscheduler.dao.entity.Project;
import org.apache.dolphinscheduler.dao.mapper.v3.ProjectV3Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * project service impl
 **/
@Service
@Slf4j
public class ProjectV3ServiceImpl extends BaseServiceImpl implements ProjectV3Service {

    @Autowired
    private ProjectV3Mapper projectMapper;

    /**
     * query project details by code
     *
     * @param projectCode project code
     * @return project detail information
     */
    @Override
    @PostAuthorize("returnObject.userId == authentication.getPrincipal().getId()")
    public Project queryProject(long projectCode) {
        return projectMapper.queryProjectByCode(projectCode);
    }

    /**
     * admin can view all projects
     *
     * @param userId     user id
     * @param searchVal  search value
     * @param maxResults max result
     * @return project list which the login user have permission to see
     */
    @Override
    @Transactional
    public QueryResult<Project> listProjects(int userId, int offset, int maxResults, String searchVal) {
        QueryResult<Project> queryResult = new QueryResult<>();

        List<Project> projects = projectMapper.listProjects(
                userId,
                offset,
                maxResults,
                searchVal
        );

        queryResult.setData(projects);
        queryResult.setTotalSize(projectMapper.countProjects(userId, searchVal));

        if (!projects.isEmpty()) {
            queryResult.setNextToken(PaginationUtils.encodeNextToken(String.valueOf(projects.get(projects.size() - 1).getId())));
        }

        return queryResult;
    }
}
