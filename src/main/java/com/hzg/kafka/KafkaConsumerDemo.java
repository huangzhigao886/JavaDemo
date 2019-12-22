package com.hzg.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

/**
 * kafkaConsumer
 *
 * @Author: huangzhigao
 * @Date: 2019/12/22 22:14
 */
public class KafkaConsumerDemo {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop01:9092,hadoop02:9092,hadoop03:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //自动提交offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        //设置消费组，只要groupId相同，就属于同一个组，groupId任意取名
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "1200");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer(properties);

        //指定订阅者
        kafkaConsumer.subscribe(Arrays.asList("Topic1"));

        //消费数据
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("偏移量：" + record.offset() + ",值：" + record.value());
            }
//            //同步提交offset
//            kafkaConsumer.commitSync();
//            //异步提交offset
//            kafkaConsumer.commitAsync();
        }

    }
}
