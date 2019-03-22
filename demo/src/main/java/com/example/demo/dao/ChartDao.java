package com.example.demo.dao;

import com.example.demo.model.field.Field;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public interface ChartDao {

    String TABLE_NAME = "chart";
    String HEAD = "head";
    String BODY = "body";
    String VALID = "valid";
    String FIELDS = "fields";
    String TYPES = "types";

    // 通过关键字搜索表名
    Collection<String> searchNamesByKeys(String keys) throws IOException;

    // 通过表名获取可用图表类型
    Collection<String> getValidTypesByName(String name) throws IOException;

    // 通过表名获取所有字段名称和类型
    Map<String, String> getMetadataByName(String name) throws IOException;

    // 通过参数获取图表数据
    Map<String, Field> getFieldsByChartName(String name) throws IOException ;
}
