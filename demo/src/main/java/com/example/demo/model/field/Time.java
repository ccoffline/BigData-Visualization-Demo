package com.example.demo.model.field;

import com.example.demo.util.BytesBuilder;
import com.example.demo.util.BytesParser;
import com.google.gson.Gson;
import org.apache.hadoop.hbase.util.Bytes;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Time extends Field {

    private LocalDateTime[] times;
    private byte[] bytes;

    @Override
    public void setData(String[] data) {
        times = null;
        bytes = null;

        if (data == null) return;
        LocalDateTime[] temp = new LocalDateTime[data.length];
        BytesBuilder builder = new BytesBuilder(data.length);
        for (int i = 0; i < data.length; i++)
            builder.put(Bytes.toBytes((temp[i] = LocalDateTime.parse(data[i])).toString()));

        times = temp;
        bytes = builder.build();
    }

    @Override
    public void setData(byte[] data) {
        times = null;
        bytes = null;

        if (data == null) return;
        BytesParser parser = new BytesParser(data);
        LinkedList<LocalDateTime> list = new LinkedList<>();
        byte[] temp;
        while ((temp = parser.get()) != null)
            list.add(LocalDateTime.parse(Bytes.toString(temp)));

        times = (LocalDateTime[]) list.toArray();
        bytes = data;

    }

    @Override
    public String[] getData() {
        if (times == null) return null;
        String[] temp = new String[times.length];
        for (int i = 0; i < temp.length; i++)
            temp[i] = times[i].toString();
        return temp;
    }

    @Override
    public byte[] toBytes() {
        return bytes;
    }
}
