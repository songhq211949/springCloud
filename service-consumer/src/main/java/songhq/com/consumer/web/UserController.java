package songhq.com.consumer.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import songhq.com.consumer.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value ="/getUser",method=RequestMethod.POST)
	public Map<String,Object> getUser(@RequestParam("name") String name,@RequestParam("age")int age){
		
		return userService.getUser(name, age);
		
	}
}
