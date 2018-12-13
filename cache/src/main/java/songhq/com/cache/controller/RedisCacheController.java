package songhq.com.cache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import songhq.com.cache.service.RedisCacheService;
import songhq.com.cache.vo.MiguSession;

/**
 * 
 * 
 * 测试redis做缓存
 * 
 * @author Administrator
 *
 */
@RestController()
@RequestMapping("redisCache")
public class RedisCacheController {
	
	@Autowired
	private RedisCacheService redisCacheService;
	
	
	@RequestMapping("/getSession")
	public MiguSession getMiguSession(){
		
		return redisCacheService.getSession("user001", "token001");
	}
	
	
	
	

}
