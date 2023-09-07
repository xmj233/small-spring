package org.example.springframework.beans;

import org.example.springframework.beans.factory.DisposableBean;
import org.example.springframework.beans.factory.InitializingBean;

public class UserService implements InitializingBean, DisposableBean {

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

    @Override
    public void destroy() throws Exception {
        System.out.println("userService.destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("userService.afterPropertiesSet");
    }
}
