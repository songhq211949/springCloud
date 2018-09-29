package songhq.com.cache.service;

import java.util.Date;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import songhq.com.cache.vo.MiguSession;

@Service
public class EhcacheManager {
	/**
	 * 
	 * value="cacheTest",对应ehcache.xml配置的cache
	 *  cacheManager 指定缓存管理者  
	 *  key  级获取缓存的对象的key
	 * @param userId
	 * @param userToken
	 * @return
	 */
	@Cacheable(value="cacheTest", cacheManager="ehCacheCacheManager", key="'migusession_'+#userId+'_'+#userToken")
	public MiguSession getSession(String userId, String userToken) {
		MiguSession miguSession = new MiguSession();
		miguSession.setDate(new Date());
		miguSession.setSessionId(userId+userToken);
		miguSession.setUserId(userId);
		miguSession.setUserToken(userToken);
		return miguSession;
	}
	
	@CacheEvict(value="cacheTest", cacheManager="ehCacheCacheManager", key="'migusession_'+#userId+'_'+#userToken")
	public void deleteMiguSessionCache(String userId, String userToken){
		
		System.out.println("清除缓存");
	}
	
	
	
	
	
	
	
	
	
	
}
