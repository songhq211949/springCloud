package songhq.com.cache.service;

import java.util.Date;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import songhq.com.cache.vo.MiguSession;

@Service
public class RedisCacheManager {

	 
		@Cacheable(value="cacheTest", cacheManager="redisCacheManager", key="'migusession_'+#userId+'_'+#userToken")
		public MiguSession getSession(String userId, String userToken) {
			MiguSession miguSession = new MiguSession();
			miguSession.setDate(new Date());
			miguSession.setSessionId(userId+userToken);
			miguSession.setUserId(userId);
			miguSession.setUserToken(userToken);
			return miguSession;
		}  
	
		
		
}
