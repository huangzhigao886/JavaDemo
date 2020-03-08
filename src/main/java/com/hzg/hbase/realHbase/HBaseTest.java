package com.hzg.hbase.realHbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Map;
import java.util.NavigableMap;

/**
 * Created by 84098 on 2020/3/8.
 */
public class HBaseTest {
    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "hadoop1,hadoop2,hadoop3");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
//        conf.setClassLoader(HBaseTest.class.getClassLoader());
//        conf.addResource("D://hbase-site.xml");
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("hzg:student"));
        if (table == null) {
            System.out.println("表不存在");
        }

        insertData(table);
        Scan scan = new Scan();
//        scan.withStopRow()

//        Get get = new Get(Bytes.toBytes("row2"));
//        Result result = table.get(get);
//        byte[] value = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("age"));
//        System.out.println(Bytes.toString(value));
    }


    public static void insertData(Table table) throws IOException {
        Put put = new Put(Bytes.toBytes("row123"));
        put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("class"),Bytes.toBytes(16));
        table.put(put);
    }
}
