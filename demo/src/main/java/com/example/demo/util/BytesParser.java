package com.example.demo.util;

import org.apache.hadoop.hbase.util.Bytes;

public class BytesParser {

    private final byte[] bytes;
    private int off = 0;

    public BytesParser(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] get() {
        if (off >= bytes.length) return null;
        int len = Bytes.toInt(bytes, off, 4);
        off += len + 4;
        return Bytes.copy(bytes, off - len, len);
    }
}
