package songhq.com.cache.ehcacherediscache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 实例化出EhcacheReids的缓存Manager
 * @author Administrator
 *
 */

@Configuration
@PropertySource(value = { "classpath:/ehcacheRedisCache/ehredis.properties"})
@EnableCaching
public class CacheBeanManager {
	
	
	
	
	@Autowired
	private EhcacheRedisCacheManager ehcacheRedisCacheManager;
	

	@Bean("testManager")
	public SimpleCacheManager getSimpleCacheManager(@Value("${test.ehcache.name}")String ehcacheName, @Value("${test.redis.expiration}")Long expiration){ 
		
		return ehcacheRedisCacheManager.getSimpleCacheManager(ehcacheName, expiration);
	}
	

}
