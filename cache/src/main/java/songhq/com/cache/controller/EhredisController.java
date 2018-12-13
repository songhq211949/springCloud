package songhq.com.cache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import songhq.com.cache.service.EhredisService;
import songhq.com.cache.vo.MiguSession;

/**
 * 测试组合缓存
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/ehredis")
public class EhredisController {
	
	@Autowired
	private EhredisService ehredisService;
	
	@RequestMapping("/getSession")
	public MiguSession getMiguSession(){
		
		return ehredisService.getSession("user001", "token001");
	
	
	}
}
