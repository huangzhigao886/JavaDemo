package com.hzg.hbase.realHbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

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

//        insertData(table);
        Scan scan = new Scan();
        scan.withStartRow(Bytes.toBytes("row4500"));
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()) {
            Result next = iterator.next();
            System.out.print(Bytes.toString(next.getRow())+"=============");
            System.out.println(Bytes.toString(next.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"))));
        }
//            Get get = new Get(Bytes.toBytes("row2"));
//            Result result = table.get(get);
//            byte[] value = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("age"));
//            System.out.println(Bytes.toString(value));
    }


    public static void insertData(Table table) throws IOException {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("0000");
        List<Put> putList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            Put put = new Put(Bytes.toBytes("row" + format.format(i)));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("id"), Bytes.toBytes(i));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("tom" + i));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("age"), Bytes.toBytes(String.valueOf(i % 100)));
            putList.add(put);
            if (putList.size() >= 1000) {
                table.put(putList);
                putList.clear();
            }
        }
        table.put(putList);
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
