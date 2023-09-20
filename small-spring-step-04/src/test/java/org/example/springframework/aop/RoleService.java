package org.example.springframework.aop;

public class RoleService implements IRoleService {

    public String getRole() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "用户角色：架构师";
    }

    public String addRole() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "添加角色成功: 高级软件工程师";
    }

}
