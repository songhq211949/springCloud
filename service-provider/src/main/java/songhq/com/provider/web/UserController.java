package songhq.com.provider.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

	@ResponseBody
	@RequestMapping(value ="/getUser",method=RequestMethod.POST)
	public Map<String,Object> getUser(@RequestParam("name") String name,@RequestParam("age")int age){
		HashMap<String, Object> userMap = new HashMap<String,Object>();
		userMap.put("name", name);
		userMap.put("age", age);
		return userMap;
		
	}
}
