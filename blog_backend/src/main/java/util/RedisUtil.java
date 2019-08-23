package util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;

@Component("redisUtil")
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;


    public Set getAllByPattern(String pattern){
        return redisTemplate.keys(pattern);
    }

    public  boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    public  Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    public  boolean set(String key,Object value){
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 删除缓存
     * @param key 可以传入多个值
     * @return
     */
    public  void del(String... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
//递增
    public long incr(String key,long delta){
        return redisTemplate.opsForValue().increment(key,delta);
    }
    public long hincr(String key,String item,long delta){
        return redisTemplate.opsForHash().increment(key,item,delta);
    }

    //递减
    public  long decr(String key,long delta){
        return redisTemplate.opsForValue().increment(key,-delta);
    }
    public long hdecr(String key, String item,long delta){
        return redisTemplate.opsForHash().increment(key,item,-delta);
    }
    /**
     * hashget
     * @param key 不能为空
     * @param item 不能为空
     * @return
     */
    public Object hget(String key,String item){
        return redisTemplate.opsForHash().get(key,item);
    }

    /**
     * 获取当前key的所有键值对
     * @param key
     * @return
     */
    public Map<String,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * hashset
     * @param key
     * @param map
     * @return
     */
    public boolean hmset(String key, Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param key
     * @param item
     * @param value
     * @return
     */

    public boolean hset(String key,String item,Object value){
        try{
            redisTemplate.opsForHash().put(key,item,value);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean hHasKey(String key,String item){
        return redisTemplate.opsForHash().hasKey(key,item);
    }

    /**
     * 删除hash表中的值
     * @param key
     * @param item 可以为多个
     */
    public void hdel(String key,Object... item){
        redisTemplate.opsForHash().delete(key,item);
}

    public Cursor scan(String key){
        return redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
    }
}
