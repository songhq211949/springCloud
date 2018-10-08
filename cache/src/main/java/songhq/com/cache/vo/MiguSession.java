package songhq.com.cache.vo;
import java.io.Serializable;
import java.util.Date;

public class MiguSession implements Serializable{
	//最好实现序列化接口Serializable，缓存保存在硬盘上
	private static final long serialVersionUID = 1L;

	//TODO:补充
	private String sessionId;
	
	private String userId;
	
	private String userToken;

	//用于验证第二次是在缓存中取
	private Date date;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "MiguSession [sessionId=" + sessionId + ", userId=" + userId + ", userToken=" + userToken + ", date="
				+ date + "]";
	}
	
	
	
	
	
	
}
