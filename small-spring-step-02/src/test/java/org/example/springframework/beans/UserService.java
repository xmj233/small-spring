package org.example.springframework.beans;

public class UserService {

    private String uid;

    private UserDao userDao;

    public void queryUserInfo() {
        String userName = userDao.queryUserInfo(uid);
        System.out.println(userName);
    }
}
