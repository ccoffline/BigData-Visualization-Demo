package com.example.demo.model.field;

import com.example.demo.util.BytesBuilder;
import com.example.demo.util.BytesParser;
import org.apache.hadoop.hbase.util.Bytes;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Value extends Field {

    private BigDecimal[] values;
    private byte[] bytes;

    @Override
    public void setData(String[] data) {
        values = null;
        bytes = null;

        if (data == null) return;
        BytesBuilder builder = new BytesBuilder(data.length);
        BigDecimal[] temp = new BigDecimal[data.length];
        for (int i = 0; i < data.length; i++)
            builder.put(Bytes.toBytes(temp[i] = new BigDecimal(data[i])));

        values = temp;
        bytes = builder.build();
    }

    @Override
    public void setData(byte[] data) {
        values = null;
        bytes = null;

        if (data == null) return;
        BytesParser parser = new BytesParser(data);
        LinkedList<BigDecimal> list = new LinkedList<>();
        byte[] temp;
        while ((temp = parser.get()) != null)
            list.add(Bytes.toBigDecimal(temp));

        values = (BigDecimal[]) list.toArray();
        bytes = data;
    }

    @Override
    public Object[] getData() {
        return values;
    }

    @Override
    public byte[] toBytes() {
        return bytes;
    }


}
