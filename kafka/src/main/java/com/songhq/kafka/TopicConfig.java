package com.songhq.kafka;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;


//创建topic,默认创建的topic的partition的数量为1,replica 的个数为0，replica参数在kafka集群中经常使用


/*@Configuration
@EnableKafka
public class TopicConfig {
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic foo() {
        //第一个是参数是topic名字，第二个参数是分区个数，第三个是topic的复制因子个数
        //当broker个数为1个时会创建topic失败，
        //提示：replication factor: 2 larger than available brokers: 1
        //只有在集群中才能使用kafka的备份功能
        return new NewTopic("foo", 10, (short) 2);
    }

    @Bean
    public NewTopic bar() {
        return new NewTopic("bar", 10, (short) 2);
    }

    @Bean
    public NewTopic topic1(){
        return new NewTopic("topic1", 10, (short) 2);
    }

    @Bean
    public NewTopic topic2(){
        return new NewTopic("topic2", 10, (short) 2);
    }
}
*/