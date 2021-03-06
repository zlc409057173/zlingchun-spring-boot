package com.zlingchun.jpa.pojo;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

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
@Table(name = "cst_account")
@EntityListeners(AuditingEntityListener.class)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid")
    private Long aid;

    @Column(name = "password", length = 16)
    private String password;

    @Column(name = "username", length = 32, nullable = false)
    private String username;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

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
}
