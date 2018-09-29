package songhq.com.cache.ehcache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;//在spring-context-support模块
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
/**
 * 注解的方式整合ehcache
 *
 */
@Configuration
@EnableCaching //启用缓存
public class EncacheConfig {
	
	
	@Bean
	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
		EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
		ehCacheCacheManager.setCacheManager(bean.getObject());
		return ehCacheCacheManager;
	}
	
	
	/*
	 * 据shared与否的设置,Spring分别通过CacheManager.create()或new
	 * CacheManager()方式来创建一个ehcache基地.
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setShared(true);
		//Default is "ehcache.xml" in the root of the class path
		//cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("**/**/ehcache.xml")); 
		return cacheManagerFactoryBean;
	}
	
	
	

}
             