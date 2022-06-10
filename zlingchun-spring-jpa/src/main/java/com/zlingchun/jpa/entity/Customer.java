package com.zlingchun.jpa.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author achun
 * @create 2022/6/10
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

    @CreatedBy
    String createdBy;
    @LastModifiedBy
    String modifiedBy;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate = new Date();
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP) //指定时间类型
            Date modifiedDate = new Date();

    private @Version Long version; // 指定版本号

    @Transient // 表示该字段不需要创建和更新
    private String realName;

    public Customer(String custName, String custAddress, String realName) {
        this.custName = custName;
        this.custAddress = custAddress;
        this.realName = realName;
    }
}
