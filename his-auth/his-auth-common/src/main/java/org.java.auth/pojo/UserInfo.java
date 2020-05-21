package org.java.auth.pojo;

/**
 * 载荷对象
 */
public class UserInfo {

    private String id;  //用户编号

    private String username;  //用户名

    private String name;  //用户职位

    private String picture; //用户图片

    public UserInfo() {
    }

    public UserInfo(String id, String username, String name, String picture) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.picture = picture;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}