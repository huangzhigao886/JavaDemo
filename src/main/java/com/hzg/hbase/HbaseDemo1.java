package com.hzg.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import javax.sound.midi.Soundbank;
import javax.xml.transform.sax.SAXSource;
import java.io.IOException;

/**
 * Created by 84098 on 2020/3/1.
 */
public class HbaseDemo1 {
    public static void main(String[] args) throws IOException {
        //获取Hbase配置信息，需要把habse的文件放在resource下
        Configuration conf = HBaseConfiguration.create();
        //获取数据库连接
        Connection connection = ConnectionFactory.createConnection(conf);
        //获取操作对象，Admin更像是管理表，管理命名空间 （DDL create,drop,alter)
        Admin admin = connection.getAdmin();

//        //判断某张表是否存在
//        TableName tableName = TableName.valueOf("tableNAME");
//
//        //判断表是否存在
//        boolean b = admin.tableExists(tableName);

        //操作数据库

        //1.判断命名空间 namespace,如果没找着，会抛异常
        try {
            NamespaceDescriptor namespaceDescriptor = admin.getNamespaceDescriptor("xxx");
        } catch (NamespaceNotFoundException e) {
            //创建表空间
            NamespaceDescriptor namespace1 = NamespaceDescriptor.create("namespace1").build();
            admin.createNamespace(namespace1);
        }

        //2判断表是否存在
        TableName tableName1 = TableName.valueOf("namespace1:table1");

        boolean b1 = admin.tableExists(tableName1);
        if (b1) {
            //表存在，查数据，或者新增数据
            //DML(数据操作语言 delete ,update ,insert ) DQL(select)

            //获取表对象
            Table table = connection.getTable(tableName1);


            //查询数据分为两种 get和scan
            testGet(table);


        } else {
            //创建表

            //创建表描述对象
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName1);

            //创建列簇
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("info");
            hTableDescriptor.addFamily(hColumnDescriptor);

            //键表
            admin.createTable(hTableDescriptor);
            System.out.println("表创建成功");
        }

//        Table table = connection.getTable(tableNAME);
    }


    /**
     * 通过get获取数据,只能通过rowkey去获取数据
     *
     * @param table
     */
    private static void testGet(Table table) throws IOException {
        //通过hbase提供的工具类，将rowkey变为字节数组

        String rowkey = "1001";
        Get get = new Get(Bytes.toBytes(rowkey));
        //如果想获取这一行某个列的数据可以指定列名
        //get.addColumn(Bytes.toBytes("family"), Bytes.toBytes("colum"));

        Result result = table.get(get);
        if (result.isEmpty()) {
            //说明这个key没有数据,则增加数据
            Put put = new Put(Bytes.toBytes(rowkey));
            //增加数据，需要增加列簇，列名，列值
            String family = "info";
            String qualifier = "name";
            String value = "zhangsan";
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            table.put(put);
            System.out.println("增加数据成功");
        } else {
            //展示数据
            for (Cell cell : result.rawCells()) {
                //为了获取数据方便，使用CloneUtil来取列簇，列名。列值，rowkey
                // CellUtil.cloneValue(cell)获取到的是字节数组，因此需要获取字符串。用Bytes.toString(byte[])
                System.out.println("value is: " + Bytes.toString(CellUtil.cloneValue(cell)));
                System.out.println("Column is " + Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.println("family is " + Bytes.toString(CellUtil.cloneFamily(cell)));
                System.out.println("rowkey is " + Bytes.toString(CellUtil.cloneRow(cell)));
            }
        }


    }


    /**
     * 批量查数据
     *
     * @param table
     * @throws IOException
     */
    public void testScan(Table table) throws IOException {
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            for (Cell cell : result.rawCells()) {
                //为了获取数据方便，使用CloneUtil来取列簇，列名。列值，rowkey
                // CellUtil.cloneValue(cell)获取到的是字节数组，因此需要获取字符串。用Bytes.toString(byte[])
                System.out.println("value is: " + Bytes.toString(CellUtil.cloneValue(cell)));
                System.out.println("Column is " + Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.println("family is " + Bytes.toString(CellUtil.cloneFamily(cell)));
                System.out.println("rowkey is " + Bytes.toString(CellUtil.cloneRow(cell)));
            }
        }
    }


    /**
     * 删除表
     *
     * @param connection
     * @throws IOException
     */
    public void deleteTable(Connection connection) throws IOException {
        TableName table = TableName.valueOf("tebale");
        Admin admin = connection.getAdmin();
        if (admin.tableExists(table)) {
            //先禁用表后删除表
            admin.disableTable(table);
            admin.deleteTable(table);
        }

    }
}
