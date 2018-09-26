package songhq.com.provider.vo;

import java.util.Date;

public class Message {
	
	private long id;
	
	private String msg;
	
	private Date sandTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getSandTime() {
		return sandTime;
	}

	public void setSandTime(Date sandTime) {
		this.sandTime = sandTime;
	}
	

}
