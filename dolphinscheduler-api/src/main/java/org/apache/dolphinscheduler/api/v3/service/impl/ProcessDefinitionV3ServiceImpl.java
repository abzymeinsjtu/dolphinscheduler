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
import org.apache.dolphinscheduler.api.v3.service.ProcessDefinitionV3Service;
import org.apache.dolphinscheduler.dao.entity.*;
import org.apache.dolphinscheduler.dao.mapper.v3.ProcessDefinitionV3Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * process definition service impl
 */
@Service
@Slf4j
public class ProcessDefinitionV3ServiceImpl extends BaseServiceImpl implements ProcessDefinitionV3Service {

    @Autowired
    private ProcessDefinitionV3Mapper processDefinitionMapper;


    /**
     * query process definition simple list
     *
     * @param loginUser login user
     * @param projectCode project code
     * @return definition simple list
     */

    /**
     * query process definition list paging
     *
     * @param projectCode project code
     * @param searchVal   search value
     * @param userId      user id
     * @return process definition page
     */
    @Override
    public QueryResult<ProcessDefinition> listProcessDefinitions(int userId,
                                                                 long projectCode,
                                                                 String searchVal,
                                                                 int offset,
                                                                 int maxResults) {
        QueryResult<ProcessDefinition> queryResult = new QueryResult<>();

        List<ProcessDefinition> processDefinitionList = processDefinitionMapper.listProcessDefinitions(searchVal, userId, projectCode, offset, maxResults);

        if (!processDefinitionList.isEmpty()) {
            String nextToken = PaginationUtils.encodeNextToken(
                    processDefinitionList.get(processDefinitionList.size() - 1).getId().toString()
            );
            queryResult.setNextToken(nextToken);
        }

        queryResult.setData(processDefinitionList);
        queryResult.setTotalSize(processDefinitionMapper.countProcessDefinitions(searchVal, userId, projectCode));
        return queryResult;
    }


    /**
     * query detail of process definition
     *
     * @param projectCode project code
     * @param code        process definition code
     * @return process definition detail
     */
    @Override
    public ProcessDefinition queryProcessDefinitionByCode(long projectCode, long code) {
        return processDefinitionMapper.queryProcessDefinitionByCode(code);
    }
}
