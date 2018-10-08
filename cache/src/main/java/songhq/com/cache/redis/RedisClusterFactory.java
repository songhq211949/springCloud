package songhq.com.cache.redis;


import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 实现FactoryBean接口，实现接口里面的三个方法，主要用来自定义对象，注意与beanFactory的区别
 * 实现InitializingBean接口，完成对象的初始化  
 * @author 
 */
//FactoryBean<JedisCluster>这个泛型指的是这个工厂bean返回的是JedisCluster对象的实例
@Service("redisCluster")
@PropertySource(value = "classpath:/rediscluster.properties")
public class RedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean {

	
	
	@Value("${redisCluster.ip}")
    private String addressConfig;
	
	private JedisCluster jedisCluster;
	
    @Autowired
	private JedisPoolConfig jedisPoolConfig;
	
    @Value("${redisCluster.timeout}")
    private Integer timeout;
    
    @Value("${redisCluster.maxRedirections}")
    private Integer maxRedirections;
    
    private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
 
    public JedisCluster getObject() throws Exception {
        return jedisCluster;
    }
 
    public Class<? extends JedisCluster> getObjectType() {
        return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);
    }
 
    public boolean isSingleton() {
        return true;
    }
 
    private Set<HostAndPort> parseHostAndPort() throws Exception {
        try {
            String[] ipList = this.addressConfig.split(",");
 
            Set<HostAndPort> haps = new HashSet<HostAndPort>();
            for (String ip : ipList) {
                boolean isIpPort = p.matcher(ip).matches();
                if (!isIpPort) {
                    throw new IllegalArgumentException("ip 或 port 不合法");
                }
                String[] ipAndPort = ip.split(":");
                HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
                haps.add(hap);
            }
            return haps;
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new Exception("解析 jedis 配置文件失败", ex);
        }
    }
     
    public void afterPropertiesSet() throws Exception {
        Set<HostAndPort> haps = this.parseHostAndPort();
        jedisCluster = new JedisCluster(haps, timeout, maxRedirections,jedisPoolConfig);
    }

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Pattern getP() {
		return p;
	}

	public void setP(Pattern p) {
		this.p = p;
	}

	public void setAddressConfig(String addressConfig) {
		this.addressConfig = addressConfig;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}

	public void setMaxRedirections(Integer maxRedirections) {
		this.maxRedirections = maxRedirections;
	}
    
}