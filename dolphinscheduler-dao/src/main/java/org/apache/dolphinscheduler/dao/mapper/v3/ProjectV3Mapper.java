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

import org.apache.dolphinscheduler.dao.entity.Project;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * project mapper interface
 */
public interface ProjectV3Mapper extends BaseMapper<Project> {

    Project queryProjectByCodeForUpdate(@Param("userId") int userId, @Param("projectCode") long projectCode);

    Project queryProjectByName(@Param("projectName") String projectName);

    List<Project> listProjects(
                               @Param("userId") int userId,
                               @Param("offset") int offset,
                               @Param("maxResults") int maxResults,
                               @Param("searchVal") String searchVal);
}
