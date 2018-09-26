package songhq.com.provider.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import songhq.com.provider.vo.Message;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
	
	
	final ObjectMapper mapper = new ObjectMapper();
	
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	@ResponseBody
	@RequestMapping("/sendMessage")
	public String sendMeaage(@RequestParam("message") String message) throws JsonProcessingException{
		
		Message message2 = new Message();
		message2.setId(Math.subtractExact(0, 10000l));
		message2.setMsg(message);
		message2.setSandTime(new Date());
		String sendMessage = mapper.writeValueAsString(message2);
		kafkaTemplate.send("shuaige", sendMessage);
		kafkaTemplate.send("shuaige", sendMessage);
		return  sendMessage;
	}
	
	@ResponseBody
	@RequestMapping("/sendMessageContainKey")
	public String sendMeaageContainKey(@RequestParam("message") String message) throws JsonProcessingException{
		
		Message message2 = new Message();
		message2.setId(Math.subtractExact(0, 10000l));
		message2.setMsg(message);
		message2.setSandTime(new Date());
		String sendMessage = mapper.writeValueAsString(message2);
		kafkaTemplate.send("shuaige", "shuaige_key",sendMessage);
		return  sendMessage;
	}
	
	@ResponseBody
	@RequestMapping("/sendMessageNew")
	public String sendMeaageNew(@RequestParam("message") String message) throws JsonProcessingException{
		
		Message message2 = new Message();
		message2.setId(Math.subtractExact(0, 10000l));
		message2.setMsg(message);
		message2.setSandTime(new Date());
		String sendMessage = mapper.writeValueAsString(message2);
		kafkaTemplate.send("new", "new_key",sendMessage);
		return  sendMessage;
	}
	

}
