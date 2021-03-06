package cn.xiaoyu.springsecurity.domain;

import org.springframework.stereotype.Component;

/**
 * 资源表resource对应的类
 */
@Component
public class MyResourceBean {
    private Long id;
    private String url;
    private String roles;

    public String[] getRolesArray() {
        String[] authorities = roles.split(",");
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public MyResourceBean() {
    }

    public MyResourceBean(Long id, String url, String roles) {
        this.id = id;
        this.url = url;
        this.roles = roles;
    }
}
