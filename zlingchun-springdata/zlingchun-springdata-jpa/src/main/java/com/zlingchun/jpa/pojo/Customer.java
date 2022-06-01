package com.zlingchun.jpa.pojo;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author achun
 * @create 2022/5/31
 * @description descrip
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "customer_id")
    private List<Message> messages;

    /**
     * mappedBy 将外键约束执行交给另一方维护(通常在双向关联关系中，会放弃一方的外键约束） mappedBy = "另一方关联的属性名"
     * cascade 设置关联操作
     *     CascadeType.ALL,     所有持久化操作
     *     CascadeType.PERSIST, 只有插入才会执行关联操作
     *     CascadeType.MERGE,   只有修改才会执行关联操作
     *     CascadeType.REMOVE,  只有删除才会执行关联操作
     *     CascadeType.REFRESH, 只有刷新才会执行关联操作
     *     CascadeType.DETACH;
     * fetch    设置加载方式
     *     FetchType.LAZY,      懒加载
     *     FetchType.EAGER      立即加载
     * orphanRemoval 是否关联移除（通常在修改的时候用）
     * optional 限制关联的对象不能为null
     *     true 可以为null, false 不可以为null
     *
     */
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn
    private Account account;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cst_cust_role",
            joinColumns = {@JoinColumn(name = "c_id")},
            inverseJoinColumns = {@JoinColumn(name = "r_id")})
    private List<Role> roles;

    @Transient // 表示该字段不需要创建和更新
    private String realName;

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

    public Customer(String custName, String custAddress, String realName) {
        this.custName = custName;
        this.custAddress = custAddress;
        this.realName = realName;
    }

    public Customer(Long custId, String custName, String custAddress, String realName) {
        this.custId = custId;
        this.custName = custName;
        this.custAddress = custAddress;
        this.realName = realName;
    }
}
