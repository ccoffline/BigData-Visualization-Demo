package com.example.demo.dao.impl;

import com.example.demo.dao.ChartDao;
import com.example.demo.dao.LabelDao;
import com.example.demo.dao.connector.Connector;
import com.example.demo.model.field.Field;
import com.example.demo.util.BytesParser;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Repository
public class ChartDaoImpl implements ChartDao {

    private Connector connector;
    private LabelDao labelDao;


    public ChartDaoImpl(Connector connector, LabelDao labelDao) {
        this.connector = connector;
        this.labelDao = labelDao;
    }

    @Override
    public Collection<String> searchNamesByKeys(String keys) throws IOException {
        return labelDao.searchCharts(keys);
    }

    @Override
    public Collection<String> getValidTypesByName(String name) throws IOException {
        Table table = connector.getTable(TABLE_NAME);
        Get get = connector.getCondition(name, HEAD, VALID);
        Result result = table.get(get);

        Collection<String> types = new LinkedList<>();
        BytesParser parser = new BytesParser(result.value());
        String temp;
        while ((temp = Bytes.toString(parser.get())) != null) types.add(temp);

        return types;
    }

    @Override
    public Map<String, String> getMetadataByName(String name) throws IOException {
        Table table = connector.getTable(TABLE_NAME);
        Get get = connector.getCondition(name, HEAD);
        return getMetadata(table.get(get));
    }

    @Override
    public Map<String, Field> getFieldsByChartName(String name) throws IOException {
        Table table = connector.getTable(TABLE_NAME);
        Get get = connector.getCondition(name);
        Result result = table.get(get);

        Map<String, String> metadata = getMetadata(result);
        Map<String, Field> fields = new HashMap<>();
        for (String fieldName : metadata.keySet()) {
            Field field = Field.generateField(fieldName, metadata.get(fieldName));
            if (field == null) continue;
            field.setData(connector.getCellValue(result, BODY, fieldName));
            fields.put(fieldName, field);
        }
        return fields;
    }

    private Map<String, String> getMetadata(Result result) {
        Map<String, String> fields = new HashMap<>();
        BytesParser fieldParser = new BytesParser(connector.getCellValue(result, HEAD, FIELDS));
        BytesParser typeParser = new BytesParser(connector.getCellValue(result, HEAD, TYPES));
        String temp;
        while ((temp = Bytes.toString(fieldParser.get())) != null)
            fields.put(temp, Bytes.toString(typeParser.get()));

        return fields;
    }
}
