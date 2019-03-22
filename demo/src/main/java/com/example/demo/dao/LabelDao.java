package com.example.demo.dao;

import java.io.IOException;
import java.util.Collection;

public interface LabelDao {

    String SPLITTER = " ";

    String TABLE_NAME = "label";
    String LABELS = "labels";
    String CHARTS = "charts";

    Collection<String> searchCharts(String key) throws IOException;
}
