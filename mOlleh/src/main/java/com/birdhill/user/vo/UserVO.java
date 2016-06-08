package com.birdhill.user.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserVO {

    @NotEmpty
    @Size(min = 6,max = 20)
    private String user_id;

    private String user_pw;

    @NotNull
    @Size(min = 2,max = 30)
    private String user_nm;

    @Pattern(regexp="^[_0-9a-zA-Z-]+@[0-9a-zA-Z]+(.[_0-9a-zA-Z-]+)*$")
    private String user_email;

    private String user_mobile;

    @NotNull
    @Min(18)
    private int user_age;



    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUser_pw() {
        return user_pw;
    }
    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }
    public String getUser_nm() {
        return user_nm;
    }
    public void setUser_nm(String user_nm) {
        this.user_nm = user_nm;
    }
    public String getUser_email() {
        return user_email;
    }
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
    public String getUser_mobile() {
        return user_mobile;
    }
    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }
    public int getUser_age() {
        return user_age;
    }
    public void setUser_age(int user_age) {
        this.user_age = user_age;
    }


}
