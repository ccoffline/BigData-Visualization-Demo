package com.example.demo.dao.connector;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

public interface Connector {

    Table getTable(String table) throws IOException;

    Get getCondition(String row);

    Get getCondition(String row, String family);

    Get getCondition(String row, String family, String qualifier);

    byte[] getCellValue(Result result, String family, String qualifier);
}