package com.hzg.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**异步发送API
 * @Author: huangzhigao
 * @Date: 2019/12/21 23:54
 */
public class KafkaProducerDemo {
    public static void main(String[] args) {

        //设置kafka属性
        Properties prop = new Properties();
        //指定broker地址
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop01:9092,hadoop02:9092");
        //指定key,value序列化的类型，与KafkaProducer的泛型类型相同
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //设置ack的方式，all代表等待follower全部同步完在返回ack
        prop.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        //批次大小
        prop.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        //sendRecord的等待时间
        prop.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
        //重试次数
        prop.setProperty(ProducerConfig.RETRIES_CONFIG, "1");
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer(prop);

        //发送数据
        for (int i = 0; i < 50; i++) {
            kafkaProducer.send(new ProducerRecord("test", "i", "message" + i));
        }

        //关闭生产者
        kafkaProducer.close();
    }
}
