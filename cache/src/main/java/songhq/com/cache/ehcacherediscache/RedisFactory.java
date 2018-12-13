package songhq.com.cache.ehcacherediscache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisFactory {
	
	@Autowired
	private  RedisTemplate<String, String> redisTemplate;
	
	
	
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public RedisTemplate<String, String> getRedisTemplate()
    {
        return this.redisTemplate;
    }

}
