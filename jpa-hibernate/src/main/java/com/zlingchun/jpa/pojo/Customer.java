package com.zlingchun.jpa.pojo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
@Table(name = "cst_customer")
public class Customer implements Serializable {

    /**
     * strategy:
     *      GenerationType.TABLE jpa提供的一种机制，通过一张数据库表的形成帮助我们完成主键的增长
     *      GenerationType.SEQUENCE 底层数据库必须支持序列，例如:oracle
     *      GenerationType.IDENTITY 底层数据库必须支持自动增长，例如:mysql,对id自增
     *      GenerationType.AUTO 由程序自动的帮助我们选择主键生成策略
     */
    @Id //声明主键的配置
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId; //客户主键

    @Column(name = "cust_name", length = 32, nullable = false)
    private String custName; //

    @Column(name = "cust_address", length = 128)
    private String custAddress;

    @Transient // 表示该字段不需要创建和更新
    private String realName;

    public Customer(String custName, String custAddress, String realName) {
        this.custName = custName;
        this.custAddress = custAddress;
        this.realName = realName;
    }
}
