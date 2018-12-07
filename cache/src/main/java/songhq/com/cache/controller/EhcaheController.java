package songhq.com.cache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import songhq.com.cache.service.EhcacheManager;
import songhq.com.cache.vo.MiguSession;

/**
 * 测试ehcache缓存
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/ehcache")
public class EhcaheController {
	
	@Autowired
	private EhcacheManager ehcacheManager;
	
	@RequestMapping("/migusession")
	public MiguSession getMiguSession(@RequestParam("userToken")String userId,@RequestParam("userToken")String userToken){
		
		return ehcacheManager.getSession(userId, userToken);
	}
	

	@RequestMapping("/delmigusession")
	public String deleteMiguSession(@RequestParam("userToken")String userId,@RequestParam("userToken")String userToken){
		ehcacheManager.deleteMiguSessionCache(userId, userToken);
		return "success";
	}
	
	
}
