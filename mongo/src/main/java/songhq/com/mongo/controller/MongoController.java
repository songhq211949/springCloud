package songhq.com.mongo.controller;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.WriteResult;

import songhq.com.mongo.vo.Times;

@Controller
@RequestMapping("/mongo")
public class MongoController {
	
	
	@Autowired
	//@Qualifier("mongoTemplate2")
	private MongoTemplate mongoTemplate;
	
	
	@RequestMapping("/save")
	@ResponseBody
	public String save(){
		
		Times times = new Times();
		times.setContid("content001");
		times.setFlag("zhende");
		times.setId(new BigInteger("1111111111111111111111111111111111111"));
		times.setTimes(1827183713l);
		//保存,如果主键相同的话则为更新操作，不会报错
		mongoTemplate.save(times);
		return "success";
	}
	
	@RequestMapping("/modify/{contId}/{times}")
	@ResponseBody
	public  Map<String,Object> modify(@PathVariable String contId,@PathVariable String times){
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("code", 200);
		Long timeslong = Long.valueOf(times);
		//先根据contid查询
		List<Times> timesList = mongoTemplate.find(new Query(Criteria.where("contid").is(contId)), Times.class);
		if(!CollectionUtils.isEmpty(timesList)){
			Times times2 = timesList.get(0);
			Update update = new Update();
			//给播人工播放次数设置值
			update.set("manualTimes", timeslong);
			//mongoDB修改操作
			WriteResult upsert = mongoTemplate.upsert(new  Query(Criteria.where("contid").is(contId)),update,Times.class);
			//把upsert的结果返回
			hashMap.put("upsert", upsert);
			return hashMap;
		}
		hashMap.put("message", "不存在"+contId);
		return hashMap;
	}
	
	/*public static void main(String[] args) {
		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add("a"); 
		arrayList.add("b"); 
		arrayList.add("c"); 
		List<String> subList = arrayList.subList(0, 8);
		System.out.println(subList);
	}*/
	
	
	
}
