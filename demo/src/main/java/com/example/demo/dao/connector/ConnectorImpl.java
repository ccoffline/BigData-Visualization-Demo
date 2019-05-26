package com.example.demo.dao.connector;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Aspect
public class ConnectorImpl implements Connector {

    @Override
    public Table getTable(String table) throws IOException {
        return connection.getTable(TableName.valueOf(table));
    }

    @Override
    public Get getCondition(String row) {
        return new Get(Bytes.toBytes(row));
    }

    @Override
    public Get getCondition(String row, String family) {
        return getCondition(row).addFamily(Bytes.toBytes(family));
    }

    @Override
    public Get getCondition(String row, String family, String qualifier) {
        return getCondition(row).addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
    }

    @Override
    public byte[] getCellValue(Result result, String family, String qualifier) {
        return result.getColumnLatestCell(Bytes.toBytes(family), Bytes.toBytes(qualifier)).getValueArray();
    }

    private Log log = LogFactory.getLog(Connector.class);
    private Configuration configuration;
    private Connection connection;

    public ConnectorImpl(Configuration configuration) {
        this.configuration = configuration;
//        try {
//            connect();
//        } catch (IOException e) {
//            log.info("HBase connection failed. Waiting to reconnect.");
//        }
    }

//    @Pointcut("execution(* com.example.demo.dao.*Dao.*(..))")
//    public void pointcut() {
//    }
//
//    @Around("pointcut()")
//    public Object checkConnection(ProceedingJoinPoint point) throws Throwable {
//        if (connection == null || connection.isClosed()) {
//            log.info("Connection is closed. Trying to reconnect.");
//            reconnect();
//        }
//        return point.proceed();
//    }

    private void connect() throws IOException {
        connection = ConnectionFactory.createConnection(configuration);
    }

    private void reconnect() throws IOException {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (IOException e) {
            log.warn("Closing connection failed. Trying to reconnect.");
        }
        connect();
    }
}
