package com.hzg.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hbase过滤器
 * Created by 84098 on 2020/3/1.
 */
public class HbaseFilterDemo {
    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        //获取数据库连接
        Connection connection = ConnectionFactory.createConnection(conf);
        //获取操作对象，Admin更像是管理表，管理命名空间 （DDL create,drop,alter)

        TableName tableName1 = TableName.valueOf("namespace1:table1");
        Table table = connection.getTable(tableName1);

        Scan scan = new Scan();
//        //根据列簇查询
//        scan.addFamily(Bytes.toBytes("info"));
//        //根据列名查询
//        scan.addColumn(Bytes.toBytes("info"),Bytes.toBytes("name"));

//        FilterList filterList = new FilterList();

        //字节比较器
        BinaryComparator bc = new BinaryComparator(Bytes.toBytes("1001"));
        Filter rowFilter = new RowFilter(CompareFilter.CompareOp.GREATER, bc);
//        //正则比较器 ,传入正则表达式
        RegexStringComparator rsc = new RegexStringComparator("regex");
        Filter rowFilter1  = new RowFilter(CompareFilter.CompareOp.EQUAL, rsc);

        //多个比较器,设置多个过滤器之间的与或关系
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(rowFilter);
        filterList.addFilter(rowFilter1);


        scan.setFilter(filterList);


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


        table.close();
        connection.close();
    }



    public void filter(String tableName,Connection conn) throws Exception {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        //SingleColumnValueFilter：二进制比较器，完整匹配字节数组，返回匹配到的整行
        Filter filter = new SingleColumnValueFilter(Bytes.toBytes("info"), Bytes.toBytes("personType"), CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("良民")));
        //SingleColumnValueFilter：二进制比较器，只比较前缀是否相同，返回的是匹配到的整行，并非每一列
        Filter filter0 = new SingleColumnValueFilter(Bytes.toBytes("info"), Bytes.toBytes("personType"), CompareFilter.CompareOp.EQUAL, new BinaryPrefixComparator(Bytes.toBytes("重点")));
        //SingleColumnValueFilter：匹配正则表达式，返回匹配到的整行
        Filter filter1 = new SingleColumnValueFilter(Bytes.toBytes("info"), Bytes.toBytes("personType"), CompareFilter.CompareOp.EQUAL, new RegexStringComparator(".*重点人员.*"));
        //SingleColumnValueFilter：匹配是否包含子串，大小写不敏感，返回匹配到的整行
        Filter filter2 = new SingleColumnValueFilter(Bytes.toBytes("info"), Bytes.toBytes("personType"), CompareFilter.CompareOp.EQUAL, new SubstringComparator("线索人员"));
        //查询出匹配的行，但是过滤掉所匹配的列
        Filter filter3 = new SingleColumnValueExcludeFilter(Bytes.toBytes("info"), Bytes.toBytes("personType"), CompareFilter.CompareOp.EQUAL, new SubstringComparator("线索人员"));
        //RandomRowFilter:按照一定的几率来返回随机的结果
        Filter filter4 = new RandomRowFilter((float) 0.5);
        //RowFilter:删选出指定开头行健的所有匹配的行
        Filter filter5 = new PrefixFilter(Bytes.toBytes("00"));
        //ValueFilter:按照value全数据库搜索,返回的是所匹配值的某一列，并非某一行
        Filter filter6 = new ValueFilter(CompareFilter.CompareOp.NOT_EQUAL, new BinaryComparator(Bytes.toBytes("23")));
        //按family（列族）查找，取回所有符合条件的“family”
        Filter filter7 = new FamilyFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("info")));
        //KeyOnlyFilter:返回所有的行，但是值全是空
        Filter filter8 = new KeyOnlyFilter();
        //ColumnsPrefixFilter:按照列明的前缀来筛选单元格，返回所有行的指定某列
        Filter filter9 = new ColumnPrefixFilter(Bytes.toBytes("ag"));
        //FirsterKeyOnlyFilter:返回的结果集中只包含第一列的而数据，在找到每一行的第一列后就会停止扫描
        Filter filter10 = new FirstKeyOnlyFilter();
        //InclusiveStopFilter:返回截止到指定行的所有数据，包含最后一行(005)。使用startRow以及stopRow的时候是左闭右开
        Filter filter11 = new InclusiveStopFilter(Bytes.toBytes("005"));
        //cloumnCountGetFilter:返回每行最多返回多少列，在一行列数超过一定数量的时候，结束整个表的扫描
        Filter filter12 = new ColumnCountGetFilter(6);
        //SkipFilter:附加过滤器，如果发现一行中的某一列不符合条件，则整行就会被过滤
        Filter filter13 = new SkipFilter(filter6);
        //WhileMatchFilter:过滤数据，直到不符合条件,停止扫扫描,返回的是符合条件的每一列数据
        Filter filter14 = new WhileMatchFilter(filter6);
        //QualifierFilter:列名过滤，返回指定的每一列数据
        Filter filter15 = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("age")));
        //MultipleColumnPrefixFilter:与ColumnsPrefixFilter不同的是可以指定多个列明的前缀
        byte[][] prefixs = new byte[][]{Bytes.toBytes("ag"), Bytes.toBytes("na")};
        Filter filter16 = new MultipleColumnPrefixFilter(prefixs);
        //ColumnRangeFilter:可以进行高效的列名内部扫描，因为列名是已经按照字典顺序排好的,返回[minColumn,maxColumn]之间的数据
        boolean minColumnlnclusive = true;
        boolean maxColumnlnclusive = true;
        Filter filter17 = new ColumnRangeFilter(Bytes.toBytes("name"), minColumnlnclusive, Bytes.toBytes("zjhm"), maxColumnlnclusive);
        //DependentColumnFilter:尝试找到该列所在的每一行，并返回改行具有相同时间戳的全部键值对,返回的是具体的某一列，并非某一行
        Filter filter18 = new DependentColumnFilter(Bytes.toBytes("info"), Bytes.toBytes("age"));
        //RandomRowFilter:随机选择一行的过滤器，chance是一个浮点数
        float chance = 0.6f;
        Filter filter19 = new RandomRowFilter(chance);
        //ColumnPaginationFilter:按列分页过滤器，针对列数量很多的情况使用
        int limit = 3;
        int columnOffset = 0;
        Filter filter20 = new ColumnPaginationFilter(limit, columnOffset);
        //综合过滤器使用
        List<Filter> filters = new ArrayList<>();
        filters.add(filter1);
        filters.add(filter2);
        FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

        ((SingleColumnValueFilter) filter1).setFilterIfMissing(false);
        scan.setFilter(filter20);
        ResultScanner scanner = table.getScanner(scan);
        for (Result r : scanner) {
            for (Cell cell : r.rawCells()) {
                System.out.println(
                        "Rowkey:" + Bytes.toString(r.getRow()) + "\t" +
                                "Family:Quilfifier " + Bytes.toString(CellUtil.cloneQualifier(cell)) + "\t" +
                                "value: " + Bytes.toString(CellUtil.cloneValue(cell))
                );
            }
        }
        scanner.close();
    }

}
