package songhq.com.consumer.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import songhq.com.consumer.config.ConfigService;
import songhq.com.consumer.service.UserService;


@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConfigService configService;

	//测试调用eureka服务中心的其它服务，以及断路器的功能
	@ResponseBody
	@RequestMapping(value ="/getUser",method=RequestMethod.POST)
	public Map<String,Object> getUser(@RequestParam("name") String name,@RequestParam("age")int age){
		
		return userService.getUser(name, age);
		
	}
	@ResponseBody
	@RequestMapping(value ="/getName")
	public String getName(){
		
		return configService.getName();
	}
	
	
	
	
}
