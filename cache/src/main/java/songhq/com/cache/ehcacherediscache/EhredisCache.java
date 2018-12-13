package songhq.com.cache.ehcacherediscache;

import java.util.concurrent.Callable;


import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.data.redis.cache.RedisCache;

import net.sf.ehcache.Element;

public class EhredisCache implements Cache {

	
	private EhCacheCache ehCacheCache;
	private RedisCache redisCacheA;

	private String name;

	public EhredisCache() {
		this.name = "L2";
	}

	@Override
	public String getName() {

		return this.name;
	}

	@Override
	public Object getNativeCache() {

		return null;
	}

	@Override
	public ValueWrapper get(Object key) {
		ValueWrapper valueWrapper = ehCacheCache.get(key);
		if (valueWrapper != null) {
			return valueWrapper;
		}
		valueWrapper = redisCacheA.get(key);
		if (null != valueWrapper)
			ehCacheCache.put(key, valueWrapper.get());
		return valueWrapper;
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		Object value = null;
		//优先从ehCacheCache中获取值
		try {
			value = ehCacheCache.get(key, type);
		} catch (IllegalStateException e) {
			ehCacheCache.evict(key);
		}
		if (value != null) {
			return (T) value;
		}
		try {
			value = redisCacheA.get(key, type);
			if (value != null){
				//并把值放到ehCacheCache中
				ehCacheCache.put(key, value);
				}
			return (T) value;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {

		ValueWrapper valueWrapper = ehCacheCache.get(key);
		Element element = (Element)valueWrapper.get();
		if(element != null){
			return (T) element.getObjectValue();
            }
		try
        {
            Object value = redisCacheA.get(key, valueLoader);
            if(value != null){
                ehCacheCache.get(key, valueLoader);
                }
            return (T) value;
        }
        catch(Exception e)
        {
            return null;
        }
	}

	@Override
	public void put(Object key, Object value) {
		 ehCacheCache.put(key, value);
		 redisCacheA.put(key, value);
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		ValueWrapper putIfAbsent = ehCacheCache.putIfAbsent(key, value);
		 redisCacheA.putIfAbsent(key, value);
		return putIfAbsent;
	}

	@Override
	public void evict(Object key) {
		ehCacheCache.evict(key);
		redisCacheA.evict(key);
	}

	@Override
	public void clear() {

	}

	public EhCacheCache getEhCacheCache() {
		return ehCacheCache;
	}

	public void setEhCacheCache(EhCacheCache ehCacheCache) {
		this.ehCacheCache = ehCacheCache;
	}

	public RedisCache getRedisCacheA() {
		return redisCacheA;
	}

	public void setRedisCacheA(RedisCache redisCacheA) {
		this.redisCacheA = redisCacheA;
	}

	public void setName(String name) {
		this.name = name;
	}

}
