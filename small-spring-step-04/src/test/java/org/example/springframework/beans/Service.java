package org.example.springframework.beans;

public class Service {


    private String uid;

    private String company;

    private String location;

    private IDao dao;

    public String queryUserInfo() {
        return dao.queryUserName(uid) + "," + company + "," + location;
    }

}
