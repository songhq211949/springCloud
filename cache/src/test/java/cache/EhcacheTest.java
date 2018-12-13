package cache;

import org.junit.Test;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;


public class EhcacheTest {
	
	
	
	@Test
	public void  testEhcache(){
		
		CacheManager cacheManager = CacheManager.create("E:\\springCloud\\cache\\src\\main\\resources\\ehcache.xml");
		Cache cache = cacheManager.getCache("cacheTest");
		
		System.out.println(cache);
		
		
		
	}
	

}
