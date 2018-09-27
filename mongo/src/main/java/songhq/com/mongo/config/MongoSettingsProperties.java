package songhq.com.mongo.config;



import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
//@ConfigurationProperties(prefix = "spring.data.mongodb") //方面点,直接写死
public class MongoSettingsProperties {

	//需要访问的mongo数据库
	private String database="mongoplaytime";
	
	private List<String> hosts=Arrays.asList(new String[]{"127.0.0.1"});
	private List<Integer> ports=Arrays.asList(new Integer[]{27017});
	//集群副本暂时不配
	private String replicaSet;
	private String username="songhq";
	private String password="123456";
	//用于权限认证的数据
	private String authenticationDatabase="mongoplaytime";
	private Integer minConnectionsPerHost = 10;
	private Integer connectionsPerHost = 20;
	private Integer maxConnectionIdleTime = 60;
	private Integer maxConnectionLifeTime = 60;
	private Integer connectTimeout = 3000;
	private Integer socketTimeout = 3000;

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public List<String> getHosts() {
		return hosts;
	}

	public void setHosts(List<String> hosts) {
		this.hosts = hosts;
	}

	public List<Integer> getPorts() {
		return ports;
	}

	public void setPorts(List<Integer> ports) {
		this.ports = ports;
	}

	public String getReplicaSet() {
		return replicaSet;
	}

	public void setReplicaSet(String replicaSet) {
		this.replicaSet = replicaSet;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthenticationDatabase() {
		return authenticationDatabase;
	}

	public void setAuthenticationDatabase(String authenticationDatabase) {
		this.authenticationDatabase = authenticationDatabase;
	}

	public Integer getMinConnectionsPerHost() {
		return minConnectionsPerHost;
	}

	public void setMinConnectionsPerHost(Integer minConnectionsPerHost) {
		this.minConnectionsPerHost = minConnectionsPerHost;
	}

	public Integer getConnectionsPerHost() {
		return connectionsPerHost;
	}

	public void setConnectionsPerHost(Integer connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}

	public Integer getMaxConnectionIdleTime() {
		return maxConnectionIdleTime;
	}

	public void setMaxConnectionIdleTime(Integer maxConnectionIdleTime) {
		this.maxConnectionIdleTime = maxConnectionIdleTime;
	}

	public Integer getMaxConnectionLifeTime() {
		return maxConnectionLifeTime;
	}

	public void setMaxConnectionLifeTime(Integer maxConnectionLifeTime) {
		this.maxConnectionLifeTime = maxConnectionLifeTime;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

}
