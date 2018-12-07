package songhq.com.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@ResponseBody
	@RequestMapping("/getUser")
	public String getUser(){
		return "hello";
	}
	
	
	@Bean
	public String runTime(){
		System.out.println("@Bean注解的方法运行了");
		return null;
	}
	
	

}
