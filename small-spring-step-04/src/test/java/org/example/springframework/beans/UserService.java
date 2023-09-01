package org.example.springframework.beans;

public class UserService {

    private String uid;

    private String company;

    private String location;

    private UserDao userDao;

    public void setLocation(String location) {
        this.location = location;
    }

    public void queryUserInfo() {
        String userName = userDao.queryUserInfo(uid);
        System.out.println(userName + ", 公司：" + company + ", 地点：" + location);
    }
}
