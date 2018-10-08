package songhq.com.cache.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisCluster;
import songhq.com.cache.vo.MiguSession;

@Service
public class RedisService {
	
	
	//这个是属于jedis包下
	@Autowired
	private JedisCluster jedisCluster;
	
	
	//采用redisTemplate的方式交互redis服务器（redi已是集群的布置） 这个是 spring-data-redis包下的,
	@Autowired
	private RedisTemplate redisTemplate;
	
	
	public long saveSession(MiguSession miguSession){
		
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		
         String uuid ="redisTemplate"+UUID.randomUUID().toString().replace("-", "");
         //TODO
         //没有解决的问题，是按照什么规则将数据存放到redis集群当中的哪个节点
         //这种redis集群的布置，对于新增一个节点，或者去掉一个节点，原来能够查询到的数据是否还能查询的到
         //集群的策略是什么
		Long leftPush = opsForList.leftPush(uuid, miguSession.toString());
		return leftPush;
	}
	
	
	public long saveSession2(MiguSession miguSession){
			
			ListOperations<String, String> opsForList = redisTemplate.opsForList();
			
	         String uuid ="jedisCluster"+ UUID.randomUUID().toString().replace("-", "");
	         Long sadd = jedisCluster.sadd(uuid, miguSession.toString());
			return sadd;
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
