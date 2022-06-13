package com.zlingchun.jdbc.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author achun
 * @create 2022/6/12
 * @description descrip
 */
@Data
@Builder
public class User {
    private Long id;
    private int age;
    private Date birth;
    private String password;
    private String realname;
    private String username;
}
