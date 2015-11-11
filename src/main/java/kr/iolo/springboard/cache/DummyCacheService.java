package kr.iolo.springboard.cache;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("dummyCacheService")
public class DummyCacheService implements CacheService {
    @Override
    public <T> void set(String key, T value) {
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        return null;
    }

    @Override
    public <T> List<T> getList(String key, Class<T> type) {
        return null;
    }

    @Override
    public void remove(String key) {
    }
}
