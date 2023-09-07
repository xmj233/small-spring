package org.example.springframework.beans;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    public void initDataMethod() {
        hashMap.put("10001", "zs");
        hashMap.put("10002", "ls");
        hashMap.put("10003", "ww");

        System.out.println("userDao init method");
    }

    public void destroyDataMethod() {
        System.out.println("userDao destroy method");
    }

    public String queryUserInfo(String uid) {
        return hashMap.get(uid);
    }
}
