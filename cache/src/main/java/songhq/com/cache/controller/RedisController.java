package songhq.com.cache.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import songhq.com.cache.service.RedisService;
import songhq.com.cache.vo.MiguSession;

/**
 * 
 * 为了测试  使用RedisTemplate(String 框架中带的)操作redis集群
 * 
 * 和      使用 edisCluster（jedis jar包下的）操作redis 的方式
 * 
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/redis")
public class RedisController {
	
	@Autowired
	private RedisService redisService;
	
	
	@RequestMapping("/save")
	@ResponseBody
	public String  saveSession(HttpServletRequest request, HttpServletResponse response){
		
		
		HttpSession session = request.getSession();
		
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
