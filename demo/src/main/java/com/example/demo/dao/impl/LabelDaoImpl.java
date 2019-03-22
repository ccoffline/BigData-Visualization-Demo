package com.example.demo.dao.impl;

import com.example.demo.dao.LabelDao;
import com.example.demo.dao.connector.Connector;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class LabelDaoImpl implements LabelDao {

    private Connector connector;

    public LabelDaoImpl(Connector connector) {
        this.connector = connector;
    }

    /**
     * 关键字搜索算法：
     * 1.一个关键字对应多个标签，每个标签对应多个值，取并集
     * 2.多个关键字搜索到的值，取交集
     */
    private class KeywordSearchAlgorithms {
        private Table table;
        private String labelType;

        public KeywordSearchAlgorithms(String labelType) throws IOException {
            table = connector.getTable(TABLE_NAME);
            this.labelType = labelType;
        }

        public Collection<String> search(String keys) throws IOException {
            Result all = table.get(connector.getCondition(labelType, LABELS));
            Collection<String> allLabels = new LinkedList<>();
            for (Cell c : all.listCells())
                if (exists(c))
                    allLabels.add(getColumnName(c));

            Collection<String> result = null;
            for (String key : keys.split(SPLITTER)) {
                if (key.isEmpty()) continue;

                // 一个关键字对应多个标签
                Collection<String> labels = new LinkedList<>();
                for (String label : allLabels)
                    if (label.contains(key))
                        labels.add(label);

                // 一个标签对应多个值
                Collection<String> values = new HashSet<>();
                for (String label : labels) {
                    Result r = table.get(connector.getCondition(label, labelType));
                    for (Cell c : r.listCells())
                        if (exists(c))
                            values.add(getColumnName(c));
                }

                // 与之前结果求交集
                if (result == null) result = values;
                else result.retainAll(values);
            }
            return result;
        }
    }

    @Override
    public Collection<String> searchCharts(String keys) throws IOException {
        return new KeywordSearchAlgorithms(CHARTS).search(keys);
    }

    // 获取列名
    private static String getColumnName(Cell cell) {
        return Bytes.toString(cell.getQualifierArray());
    }

    // 是否存在该列
    private static boolean exists(Cell cell) {
        return Bytes.toBoolean(cell.getValueArray());
    }
}
