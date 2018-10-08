package songhq.com.cache.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import songhq.com.cache.service.RedisService;
import songhq.com.cache.vo.MiguSession;

@Controller
@RequestMapping("/redis")
public class RedisController {
	
	@Autowired
	private RedisService redisService;
	
	
	@RequestMapping("/save")
	@ResponseBody
	public String  saveSession(){
		
		
		MiguSession miguSession = new MiguSession();
		
		miguSession.setDate(new Date());
		
		miguSession.setUserId("songhq001");
		
		miguSession.setUserToken("token001");
		
		miguSession.setSessionId("sesion001");
		
		redisService.saveSession(miguSession);
		return "success";
	}
	@RequestMapping("/save2")
	@ResponseBody
	public String  saveSession2(){
		
		
		MiguSession miguSession = new MiguSession();
		
		miguSession.setDate(new Date());
		
		miguSession.setUserId("songhq001");
		
		miguSession.setUserToken("token001");
		
		miguSession.setSessionId("sesion001");
		
		redisService.saveSession2(miguSession);
		return "success";
	}
	
	

}
