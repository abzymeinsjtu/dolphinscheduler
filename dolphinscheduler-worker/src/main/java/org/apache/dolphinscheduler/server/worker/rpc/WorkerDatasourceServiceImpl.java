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

package org.apache.dolphinscheduler.server.worker.rpc;

import org.apache.dolphinscheduler.extract.worker.IWorkerDatasourceService;
import org.apache.dolphinscheduler.plugin.datasource.api.datasource.DataSourceProcessor;
import org.apache.dolphinscheduler.plugin.datasource.api.utils.DataSourceUtils;
import org.apache.dolphinscheduler.spi.datasource.ConnectionParam;

import java.sql.Connection;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WorkerDatasourceServiceImpl implements IWorkerDatasourceService {

    @Override
    public TestConnectionResponse testConnection(TestConnectionRequest testConnectionRequest) {
        String errorMessage;
        boolean success;

        DataSourceProcessor dataSourceProcessor = DataSourceUtils.getDatasourceProcessor(
                testConnectionRequest.getDbType());

        ConnectionParam connectionParam =
                dataSourceProcessor.createConnectionParams(testConnectionRequest.getConnectionParam());

        try (Connection ignored = dataSourceProcessor.getConnection(connectionParam)) {
            errorMessage = "ok";
            success = true;
        } catch (Exception e) {
            errorMessage = e.getMessage();
            success = false;
        }

        return new TestConnectionResponse(success, errorMessage);
    }
}
