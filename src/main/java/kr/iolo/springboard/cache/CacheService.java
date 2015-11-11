package kr.iolo.springboard.cache;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface CacheService {

    <T> void set(String key, T value);

    <T> T get(String key, Class<T> type);

    default <T> T getOrSet(String key, Class<T> type, Supplier<T> supplier) {
        return Optional.ofNullable(get(key, type)).orElseGet(() -> {
            T value = supplier.get();
            if (value != null) {
                set(key, value);
            } else {
                remove(key);
            }
            return value;
        });
    }

    <T> List<T> getList(String key, Class<T> type);

    default <T> List<T> getListOrSet(String key, Class<T> type, Supplier<List<T>> supplier) {
        return Optional.ofNullable(getList(key, type)).orElseGet(() -> {
            List<T> value = supplier.get();
            if (value != null) {
                set(key, value);
            } else {
                remove(key);
            }
            return value;
        });
    }

    void remove(String key);

}
