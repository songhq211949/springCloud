package songhq.com.cache.ehcacherediscache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

@Component
public class EhcacheRedisCacheManager {
	
	
	
	@Autowired
	private RedisFactory redisFactory;
	
	
	
	public SimpleCacheManager getSimpleCacheManager(String ehcacheName, long longValue) {
		
		SimpleCacheManager ehRedisCacheManager = new SimpleCacheManager();
		//List<EhredisCache> caches = new ArrayList<EhredisCache>();
		
		List<ACacheCore> aCaches = new ArrayList<ACacheCore>();
		ACacheCore aCacheCore = new ACacheCore();
		//EhredisCache ehredisCache = new EhredisCache();
		RedisTemplate<String, String> redisTemplate = redisFactory.getRedisTemplate();
		RedisCache redisCacheA = new RedisCache("AB@", "AB@".getBytes(), redisTemplate, longValue);
		//ehredisCache.setRedisCacheA(redisCacheA);
		aCacheCore.setRedisCacheA(redisCacheA);
		//将Ehcache原生的cache管理者转换一下
		CacheManager cacheManager = CacheManager.create(this.getClass().getClassLoader().getResource("ehcacheRedisCache/ehcache2.xml"));
		Cache ehcache = cacheManager.getCache(ehcacheName);
		Cache healCache = cacheManager.getCache("HealthRedis");
		EhCacheCache ehCacheCache = new EhCacheCache(ehcache);
		EhCacheCache ehhealthCache = new EhCacheCache(healCache);
		
		aCacheCore.setEnabledA(true);
		aCacheCore.setHealthCache(ehhealthCache);
		aCacheCore.setEhCacheCache(ehCacheCache);
		aCacheCore.setName(ehcacheName);
		aCaches.add(aCacheCore);
		//ehredisCache.setEhCacheCache(ehCacheCache);
		//caches.add(ehredisCache);
		//ehRedisCacheManager.setCaches(caches);
		ehRedisCacheManager.setCaches(aCaches);
		return ehRedisCacheManager;
	}

}
