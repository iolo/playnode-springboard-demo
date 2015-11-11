package kr.iolo.springboard.cache;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component("cacheService")
public class RedisCacheService implements CacheService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public <T> void set(String key, T value) {
        stringRedisTemplate.opsForValue().set(key, stringify(value), 10, TimeUnit.SECONDS);
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        return parse(stringRedisTemplate.opsForValue().get(key), type);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> type) {
        return parseList(stringRedisTemplate.opsForValue().get(key), type);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    //---------------------------------------------------------

    private ObjectMapper objectMapper;

    private ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.configure(MapperFeature.USE_GETTERS_AS_SETTERS, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }

    public String stringify(Object obj) {
        try {
            return getObjectMapper()
                    .writeValueAsString(obj);
        } catch (Throwable t) {
            //return "undefined";
            //return null;
            return "null";
        }
    }

    // FIXME: 제네릭스는 제대로 디코딩 못함... 그거 할려면 TypeReference 같은 게 필요하지만 그건 싫은데...
    public <T> T parse(String str, Class<? extends T> type) {
        try {
            return getObjectMapper()
                    .reader(type)
                    .readValue(str);
        } catch (Throwable t) {
            return null;
        }
    }

    public <T> List<T> parseList(String str, Class<? extends T> type) {
        try {
            return getObjectMapper()
                    .reader(getObjectMapper().getTypeFactory().constructCollectionType(List.class, type))
                    .readValue(str);
        } catch (Throwable t) {
            return null;
        }
    }
}
