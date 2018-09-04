package songhq.com.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients //启用feign功能
//@EnableCircuitBreaker
public class EurekaConsumerApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(EurekaConsumerApplication.class, args);
	}

}
