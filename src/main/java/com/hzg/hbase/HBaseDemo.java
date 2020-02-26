package com.hzg.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import javax.sound.midi.Soundbank;
import java.io.IOException;

/**
 * 连接hbase
 * Created by 84098 on 2020/2/26.
 */
public class HBaseDemo {
    public static void main(String[] args) throws IOException {
        //获取Hbase配置信息，需要把habse的文件放在resource下
        Configuration conf = HBaseConfiguration.create();
        //获取数据库连接
        Connection connection = ConnectionFactory.createConnection(conf);
        //获取操作对象
//        Admin admin = connection.getAdmin();

        //判断某张表是否存在
        TableName tableNAME = TableName.valueOf("tableNAME");

        Table table = connection.getTable(tableNAME);

        Scan scan = new Scan();
        //行键的比较
        BinaryComparator bc = new BinaryComparator(Bytes.toBytes("row11"));

        RegexStringComparator rc = new RegexStringComparator("正则");


        FilterList filterList = new FilterList();

        Filter filter = new RowFilter(CompareFilter.CompareOp.GREATER_OR_EQUAL,bc);
        //设置过滤器
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {
            for (Cell cell : result.rawCells()) {
                //value
                System.out.println(CellUtil.cloneValue(cell));
                System.out.println(CellUtil.cloneRow(cell));
            }
        }

    }
}
