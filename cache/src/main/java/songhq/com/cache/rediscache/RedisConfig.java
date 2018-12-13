package songhq.com.cache.rediscache;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;
import songhq.com.cache.utils.StringUtil;

/**
 * 
 * 注解的方式配置redis集群的连接
 * @author 
 *
 */

@Configuration
@PropertySource(value = "classpath:/redis.properties")
@EnableCaching
@Primary
public class RedisConfig {
	
	//正则表达式
	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
	
	//配置jedisPool,访问redis服务客户端用的是jedis
	@Bean(name = "jedisPoolConfig") 
	@Primary
	public JedisPoolConfig poolCofig(@Value(value = "${redis.pool.max-idle}") int maxIdle,
			@Value(value = "${redis.pool.max-total}") int maxTotal,
			@Value(value = "${redis.pool.max-waitMillis}") long maxWaitMillis,
			@Value(value = "${redis.pool.testOnBorrow}") boolean testOnBorrow) {

		JedisPoolConfig poolCofig = new JedisPoolConfig();
		poolCofig.setMaxIdle(maxIdle);//最大空闲连接数
		poolCofig.setMaxTotal(maxTotal);//最大连接数
		poolCofig.setMaxWaitMillis(maxWaitMillis);//最大等待时间毫秒
		poolCofig.setTestOnBorrow(testOnBorrow);//是否创建instance
		return poolCofig;
	}
	
	//RedisClusterConfiguration  是在spring-data-redis包下
	@Bean(name = "redisClusterConfiguration")
	@Primary
	public RedisClusterConfiguration redisClusterConfiguration(@Value(value = "${redis.addressConfig}") String addressConfig,
			@Value(value = "${redis.maxRedirects}") int maxRedirects){
		List<RedisNode> redislist = new ArrayList<RedisNode>();
		String[] ipList = addressConfig.split(",");
		for(String ip : ipList){
			 boolean isIpPort = p.matcher(ip).matches();
             if (!isIpPort) {
                 throw new IllegalArgumentException("ip 或 port 不合法");
             }
             String[] ipAndPort = ip.split(":");
             RedisNode redisNode = new RedisNode(ipAndPort[0],StringUtil.stringToInteger(ipAndPort[1]));
             redislist.add(redisNode);
		}
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
		redisClusterConfiguration.setMaxRedirects(maxRedirects);
		redisClusterConfiguration.setClusterNodes(redislist);
		return redisClusterConfiguration;
	}
	

	//将jedisPool和redisClusterConfiguration注入到jedisConnectionFactory
	@Bean(name = "jedisConnectionFactory")
	@Primary
	public JedisConnectionFactory jedisConnectionFactory(@Qualifier("redisClusterConfiguration") RedisClusterConfiguration clusterConfig,
			@Qualifier("jedisPoolConfig") JedisPoolConfig poolConfig) {

		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(clusterConfig);
		jedisConnectionFactory.setPoolConfig(poolConfig);
		return jedisConnectionFactory;
	}
	
	
	@Bean(name = "redisTemplate")
	@Primary
	public RedisTemplate<String, String> redisTemplate(
			@Qualifier("jedisConnectionFactory") RedisConnectionFactory factory) {
		//指定redis模板
		StringRedisTemplate template = new StringRedisTemplate(factory);
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setKeySerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
	
	//生成缓存的管理类
	@Bean(name = "redisCacheManager")
	@Primary
	public CacheManager cacheManager(@Qualifier("redisTemplate") RedisTemplate redisTemplate) {
		RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
		// 设置缓存过期时间
		//rcm.setDefaultExpiration(60);//秒
		return rcm;
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
