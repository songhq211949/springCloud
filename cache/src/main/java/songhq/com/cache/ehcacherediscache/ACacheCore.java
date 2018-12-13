
package songhq.com.cache.ehcacherediscache;

import java.util.concurrent.Callable;
import net.sf.ehcache.Element;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.data.redis.cache.RedisCache;

public class ACacheCore
    implements Cache
{

    public ACacheCore()
    {
        name = "L2";
    }

    public String getName()
    {
        return name;
    }

    public Object getNativeCache()
    {
        return null;
    }

    public org.springframework.cache.Cache.ValueWrapper get(Object key)
    {
        org.springframework.cache.Cache.ValueWrapper value = ehCacheCache.get(key);
        if(value != null)
            return value;
        if(checkA())
        {
            value = redisCacheA.get(key);
            if(null != value)
                ehCacheCache.put(key, value.get());
        }
        return value;
    }

    public Object get(Object key, Class type)
    {
        Object value = null;
        try
        {
            value = ehCacheCache.get(key, type);
        }
        catch(IllegalStateException e)
        {
            ehCacheCache.evict(key);
        }
        if(value != null)
            return value;
        if(checkA())
            try
            {
                value = redisCacheA.get(key, type);
                if(value != null)
                    ehCacheCache.put(key, value);
                return value;
            }
            catch(Exception e)
            {
                return null;
            }
        else
            return null;
    }

    public Object get(Object key, Callable valueLoader)
    {
        org.springframework.cache.Cache.ValueWrapper valueWrapper = ehCacheCache.get(key);
        Element element = (Element)valueWrapper.get();
        if(element != null)
            return element.getObjectValue();
        if(checkA())
        {
            try
            {
                Object value = redisCacheA.get(key, valueLoader);
                if(value != null)
                    ehCacheCache.get(key, valueLoader);
                return value;
            }
            catch(Exception e)
            {
                return null;
            }
        } else
        {
            Object value = ehCacheCache.get(key, valueLoader);
            return value;
        }
    }

    public void put(Object key, Object value)
    {
        ehCacheCache.put(key, value);
        if(checkA())
            try
            {
                redisCacheA.put(key, value);
            }
            catch(Exception exception) { }
    }

    public org.springframework.cache.Cache.ValueWrapper putIfAbsent(Object key, Object value)
    {
        org.springframework.cache.Cache.ValueWrapper valueWra = ehCacheCache.putIfAbsent(key, value);
        if(checkA())
            redisCacheA.putIfAbsent(key, value);
        return valueWra;
    }

    public void evict(Object key)
    {
        ehCacheCache.evict(key);
        if(checkA())
            try
            {
                redisCacheA.evict(key);
            }
            catch(Exception exception) { }
    }

    public void clear()
    {
        ehCacheCache.clear();
        if(checkA())
            try
            {
                redisCacheA.clear();
            }
            catch(Exception exception) { }
    }

    public boolean checkA()
    {
        return enabledA && isAlive("HealthRedis_aliveA");
    }

    private boolean isAlive(String key)
    {
        org.springframework.cache.Cache.ValueWrapper alive = healthCache.get(key);
        if(null != alive)
        {
            return false;
        } else
        {
            return true;
        }
    }

    public EhCacheCache getEhCacheCache()
    {
        return ehCacheCache;
    }

    public void setEhCacheCache(EhCacheCache ehCacheCache)
    {
        this.ehCacheCache = ehCacheCache;
    }

    public RedisCache getRedisCacheA()
    {
        return redisCacheA;
    }

    public void setRedisCacheA(RedisCache redisCacheA)
    {
        this.redisCacheA = redisCacheA;
    }

    public EhCacheCache getHealthCache()
    {
        return healthCache;
    }

    public void setHealthCache(EhCacheCache healthCache)
    {
        this.healthCache = healthCache;
    }

    public boolean isEnabledA()
    {
        return enabledA;
    }

    public void setEnabledA(boolean enabledA)
    {
        this.enabledA = enabledA;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private String name;
    private EhCacheCache ehCacheCache;
    private RedisCache redisCacheA;
    private EhCacheCache healthCache;
    private boolean enabledA;
    
    
}

