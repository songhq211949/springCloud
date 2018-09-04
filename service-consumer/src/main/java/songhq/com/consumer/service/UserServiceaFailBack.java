package songhq.com.consumer.service;

import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Component;

@Component
public class UserServiceaFailBack implements UserService {
	

	@Override
	public Map<String, Object> getUser(String name, int age) {
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("code", "调用错误");
		return hashMap;
	}
}
