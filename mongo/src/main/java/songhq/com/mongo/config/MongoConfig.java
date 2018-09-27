package songhq.com.mongo.config;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

@Configuration
public class MongoConfig {

	// 注入配置实体
	@Autowired
	private MongoSettingsProperties mongoSettingsProperties;
	// 覆盖默认的MongoDbFactor
	@Bean
	public MongoDbFactory mongoDbFactory() {
		// 客户端配置（连接数、副本集群验证）
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		builder.connectionsPerHost(mongoSettingsProperties.getConnectionsPerHost());
		builder.minConnectionsPerHost(mongoSettingsProperties.getMinConnectionsPerHost());
		if (mongoSettingsProperties.getReplicaSet() != null) {
			builder.requiredReplicaSetName(mongoSettingsProperties.getReplicaSet());
		}
		builder.readPreference(ReadPreference.secondary());
		 builder.maxConnectionIdleTime(mongoSettingsProperties.getMaxConnectionIdleTime());
		 builder.maxConnectionLifeTime(mongoSettingsProperties.getMaxConnectionLifeTime());
		 builder.connectTimeout(mongoSettingsProperties.getConnectTimeout());
		 builder.socketTimeout(mongoSettingsProperties.getSocketTimeout());
		MongoClientOptions mongoClientOptions = builder.build();

		// MongoDB地址列表
		List<ServerAddress> serverAddresses = new ArrayList<>();
		for (String host : mongoSettingsProperties.getHosts()) {
			Integer index = mongoSettingsProperties.getHosts().indexOf(host);
			Integer port = mongoSettingsProperties.getPorts().get(index);

			ServerAddress serverAddress = new ServerAddress(host, port);
			serverAddresses.add(serverAddress);
		}
		System.out.println("serverAddresses:" + serverAddresses.toString());

		// 连接认证
		List<MongoCredential> mongoCredentialList = new ArrayList<>();
		if (mongoSettingsProperties.getUsername() != null) {
			mongoCredentialList.add(MongoCredential.createScramSha1Credential(mongoSettingsProperties.getUsername(),
					mongoSettingsProperties.getAuthenticationDatabase() != null
							? mongoSettingsProperties.getAuthenticationDatabase()
							: mongoSettingsProperties.getDatabase(),
					mongoSettingsProperties.getPassword().toCharArray()));
		}
		System.out.println("mongoCredentialList:" + mongoCredentialList.toString());

		// 创建客户端和Factory
		MongoClient mongoClient = new MongoClient(serverAddresses, mongoCredentialList, mongoClientOptions);
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, mongoSettingsProperties.getDatabase());
		return mongoDbFactory;
	}
	
	/*@Bean(name="mongoTemplate2")
	public MongoTemplate  mongoTemplate(){
		return new MongoTemplate(mongoDbFactory());
	}
	*/
	@Bean
	public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context,
			BeanFactory beanFactory) {
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
		MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
		try {
			mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
		} catch (NoSuchBeanDefinitionException ignore) {
		}
		//去掉_class字段，不保存_class字段
		mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return mappingConverter;
	}
}
