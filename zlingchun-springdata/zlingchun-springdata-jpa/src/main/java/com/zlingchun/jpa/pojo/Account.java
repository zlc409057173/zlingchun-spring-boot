package com.zlingchun.jpa.pojo;

import lombok.*;

import javax.persistence.*;

/**
 * @author achun
 * @create 2022/5/31
 * @description descrip
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cst_account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid")
    private Long aid;

    @Column(name = "password", length = 16)
    private String password;

    @Column(name = "username", length = 32, nullable = false)
    private String username;
}
