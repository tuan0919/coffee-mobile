package com.nlu.packages;

import java.util.HashMap;
import java.util.Map;

public class Mapper<K, V> {
    private final Map<K, V> map = new HashMap<>();

    /**
     * Lưu trữ ánh xạ từ khóa này đến giá trị tương ứng.
     * @param key Khóa để lưu trữ
     * @param value Giá trị tương ứng
     */
    public void put(K key, V value) {
        map.put(key, value);
    }

    /**
     * Trả về giá trị được ánh xạ bởi khóa, hoặc null nếu không tìm thấy khóa.
     * @param key Khóa cần truy vấn
     * @return Giá trị được ánh xạ
     */
    public V get(K key) {
        return map.get(key);
    }

    public static void main(String[] args) {
        Mapper<String, Integer> mapper = new Mapper<>();
        mapper.put("one", 1);
        mapper.put("two", 2);

        System.out.println(mapper.get("one")); // In ra "1"
        System.out.println(mapper.get("two")); // In ra "2"
    }
}

