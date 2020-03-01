package com.hzg.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created by 84098 on 2020/3/1.
 */
public class HbaseUtil {

    private static ThreadLocal<Connection> connHolder = new ThreadLocal<>();


    //如果是这样的，会有线程安全问题，当多个用户访问，而connection只保持一份，当connection被关闭，其他线程就会出问题，因此采用ThreadLocal,每个线程都保持自己的本地线程缓存
//    private static Connection connection = null;

    private HbaseUtil() {

    }

    /**
     * 获取hbase的连接
     *
     * @return
     */
    public static void makeHbaseConnnection() throws IOException {
        Connection conn = connHolder.get();
        if (conn == null) {
            Configuration conf = HBaseConfiguration.create();
            conn = ConnectionFactory.createConnection(conf);
            connHolder.set(conn);
        }
    }


    public static void insertData(String tableName,String rowkey,String family,String column,String value) throws IOException {
        Connection connection = connHolder.get();
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowkey));
        //增加数据，需要增加列簇，列名，列值
        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
        table.put(put);
        table.close();
    }




    /**
     * 关闭连接
     *
     * @throws IOException
     */
    public static void close() throws IOException {
        Connection conn = connHolder.get();
        if (conn != null) {
            conn.close();
            connHolder.remove();
        }
    }
}
