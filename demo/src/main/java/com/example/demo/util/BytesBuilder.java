package com.example.demo.util;

import org.apache.hadoop.hbase.util.Bytes;

public class BytesBuilder {

    private final byte[][] builder;
    private int off = 0;

    public BytesBuilder(int length) {
        builder = new byte[2 * length][];
    }

    public void put(byte[] b) {
        builder[off++] = Bytes.toBytes(b.length);
        builder[off++] = b;
    }

    public byte[] build() {
        return Bytes.add(builder);
    }
}
