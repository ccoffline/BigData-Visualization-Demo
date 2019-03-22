package com.example.demo.model.field;

import com.example.demo.util.BytesBuilder;
import com.example.demo.util.BytesParser;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.Serializable;
import java.util.LinkedList;

public class Category extends Field implements Serializable {

    private String[] categories;
    private byte[] bytes;

    @Override
    public void setData(String[] data) {
        categories = null;
        bytes = null;

        if (data == null) return;
        BytesBuilder builder = new BytesBuilder(data.length);
        for (String aData : data) builder.put(Bytes.toBytes(aData));

        categories = data;
        bytes = builder.build();
    }

    @Override
    public void setData(byte[] data) {
        categories = null;
        bytes = null;

        if (data == null) return;
        BytesParser parser = new BytesParser(data);
        LinkedList<String> list = new LinkedList<>();
        byte[] temp;
        while ((temp = parser.get()) != null)
            list.add(Bytes.toString(temp));

        categories = (String[]) list.toArray();
        bytes = data;
    }

    @Override
    public String[] getData() {
        return categories;
    }

    @Override
    public byte[] toBytes() {
        return bytes;
    }


}
