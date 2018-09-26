package songhq.com.provider.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

/**
 * kafka消息的监听者
 * 
 * 
 * @author Administrator
 *
 */
@Component
public class MessageConsumer {
	 protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 @KafkaListener(topics="shuaige")
	    public void listen (ConsumerRecord<?, ?> record) throws Exception {
	        System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
	    }
	 
	 @KafkaListener(topics="new")
	    public void listen2 (ConsumerRecord<?, ?> record) throws Exception {
	        System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
	    }
	 
	 
}
