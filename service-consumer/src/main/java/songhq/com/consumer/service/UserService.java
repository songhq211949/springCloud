package songhq.com.consumer.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name="euraka-provider",fallback=UserServiceaFailBack.class)
public interface UserService {
	
	
	@RequestMapping(value="/user/getUser", method=RequestMethod.POST)
	public Map<String,Object> getUser(@RequestParam("name") String name,@RequestParam("age") int age);

}
