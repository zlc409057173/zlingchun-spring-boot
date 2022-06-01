package com.zlingchun.jpa.pojo;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author achun
 * @create 2022/6/1
 * @description descrip
 */
@Data
@ToString(exclude = "customer")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cst_message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid")
    private Long mid;

    @Column(name = "msg", length = 256)
    private String msg;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
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
