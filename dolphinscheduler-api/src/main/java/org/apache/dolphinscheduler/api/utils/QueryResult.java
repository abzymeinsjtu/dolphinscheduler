package org.apache.dolphinscheduler.api.utils;

import java.util.List;

import lombok.Data;

@Data
public class QueryResult<T> {

    List<T> data;

    String nextToken;

    int totalSize;
}
